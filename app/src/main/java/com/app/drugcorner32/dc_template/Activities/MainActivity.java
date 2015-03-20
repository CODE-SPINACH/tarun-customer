package com.app.drugcorner32.dc_template.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Dialogs.PreviousOrderDialog;
import com.app.drugcorner32.dc_template.Dialogs.SearchMedicineDialog;
import com.app.drugcorner32.dc_template.Dialogs.SendPrescriptionDialog;
import com.app.drugcorner32.dc_template.Dialogs.ZoomDialog;
import com.app.drugcorner32.dc_template.Fragments.BuyMedicineFragment;
import com.app.drugcorner32.dc_template.Fragments.HomeScreenFragment;
import com.app.drugcorner32.dc_template.Fragments.OrderItemListFragment;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnFragmentInteractionListener{

    private Uri fileUri;
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.app.drugcorner32.dc_template.Helpers.helperIDGenerator.init();
        //setting the home fragment
        replaceFragment(R.id.mainActivityFrameLayout,null);
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
    }

    public void takePhoto() {
        if (isExternalStorageWritable()) {
            Calendar cal = Calendar.getInstance();
            File dir = getPicStorageDir("prescription_images");
            File imageFile = new File(dir, cal.getTimeInMillis() + ".jpg");
            if (!imageFile.exists()) {
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                imageFile.delete();
                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            fileUri = Uri.fromFile(imageFile);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            replaceFragment(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE,null);
        }
    }

    public void deliverOrderToDialog(OrderDetails details){
        PreviousOrderDialog previousOrderDialog = (PreviousOrderDialog)
                getSupportFragmentManager().findFragmentByTag(PreviousOrderDialog.TAG);
        if(previousOrderDialog != null){
            previousOrderDialog.addOrderToSelectList(details);
        }
    }

    //All fragment transactions must take place here
    public void replaceFragment(int id, Object object) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.mainActivityFrameLayout:
                HomeScreenFragment frag1 = (HomeScreenFragment) getSupportFragmentManager().
                        findFragmentByTag(HomeScreenFragment.TAG);

                if (frag1 == null)
                    frag1 = HomeScreenFragment.newInstance();

                ft.replace(R.id.mainActivityFrameLayout, frag1, HomeScreenFragment.TAG);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commitAllowingStateLoss();
                break;

            case R.id.homeScreenImageButton1:
                BuyMedicineFragment frag2 = (BuyMedicineFragment) getSupportFragmentManager().
                        findFragmentByTag(BuyMedicineFragment.TAG);
                if (frag2 == null) {
                    frag2 = BuyMedicineFragment.newInstance();
                }
                ft.replace(R.id.mainActivityFrameLayout, frag2, BuyMedicineFragment.TAG);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
                break;

            case R.layout.dialog_previous_order:
                PreviousOrderDialog previousOrderDialog = (PreviousOrderDialog) getSupportFragmentManager().findFragmentByTag(PreviousOrderDialog.TAG);
                if (previousOrderDialog == null) {
                    previousOrderDialog = new PreviousOrderDialog();
                }
                previousOrderDialog.setCartOrderItemsList((List<OrderItemDetails>) object);
                previousOrderDialog.show(getSupportFragmentManager(), PreviousOrderDialog.TAG);
                break;

            case R.layout.dialog_search_medicine:
                SearchMedicineDialog searchMedicineDialog = new SearchMedicineDialog();
                searchMedicineDialog.show(getSupportFragmentManager(), SearchMedicineDialog.TAG);
                break;

            case R.layout.dialog_send_prescripiton:
                SendPrescriptionDialog dialog = new SendPrescriptionDialog();
                dialog.show(getSupportFragmentManager(), SendPrescriptionDialog.TAG);
                break;

            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                BuyMedicineFragment frag3 = (BuyMedicineFragment) getSupportFragmentManager().
                        findFragmentByTag(BuyMedicineFragment.TAG);

                if (frag3 == null) {
                    Toast.makeText(this, "State Loss", Toast.LENGTH_SHORT).show();
                }

                frag3.addNewPrescription(fileUri);
                break;

            case R.id.prescriptionCardImageButton1:
                ZoomDialog zoomDialog = new ZoomDialog();
                zoomDialog.setImageUri((Uri) object);
                zoomDialog.show(getSupportFragmentManager(), ZoomDialog.TAG);
                break;

            case R.id.searchMedicineSearchView1:
                BuyMedicineFragment frag4 = (BuyMedicineFragment) getSupportFragmentManager().
                        findFragmentByTag(BuyMedicineFragment.TAG);

                if (frag4 == null) {
                    Toast.makeText(this, "State Loss", Toast.LENGTH_SHORT).show();
                }
                frag4.addNewMedicine(new MedicineDetails((String) object, MedicineDetails.MedicineTypes.Tablets, 32));
                break;

            case R.id.previousOrderListView:
                PreviousOrderDialog previousOrderDialog1 = (PreviousOrderDialog)
                        getSupportFragmentManager().findFragmentByTag(PreviousOrderDialog.TAG);

                if (previousOrderDialog1.isVisible()) {
                    OrderItemListFragment frag5 = (OrderItemListFragment)
                            previousOrderDialog1.getChildFragmentManager().findFragmentByTag(OrderItemListFragment.TAG);

                    if (frag5 == null)
                        frag5 = OrderItemListFragment.newInstance();

                    frag5.setSelectable(true);
                    frag5.setOrderDetails((OrderDetails) object);
                    previousOrderDialog1.setSelectedOrderDetails((OrderDetails) object);
                    previousOrderDialog1.changeFragment(frag5);
                }
                break;

            case R.id.previousOrderDialogButton1:
                BuyMedicineFragment frag6 = (BuyMedicineFragment) getSupportFragmentManager().
                        findFragmentByTag(BuyMedicineFragment.TAG);

                if (frag6 == null) {
                    Toast.makeText(this, "State Loss", Toast.LENGTH_SHORT).show();
                }

                  frag6.addNewPreviousOrders((List<OrderDetails>) object);

                break;
            default:
                break;
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPicStorageDir(String dirName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), dirName);
        file.mkdirs();
        return file;
    }
}