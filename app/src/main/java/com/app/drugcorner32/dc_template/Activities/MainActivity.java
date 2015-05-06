package com.app.drugcorner32.dc_template.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drugcorner32.dc_template.Adapters.OrderItemListAdapter;
import com.app.drugcorner32.dc_template.Adapters.OrderListAdapter;
import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Data.StatusDetails;
import com.app.drugcorner32.dc_template.Dialogs.PreviousOrderDialog;
import com.app.drugcorner32.dc_template.Dialogs.SearchMedicineDialog;
import com.app.drugcorner32.dc_template.Dialogs.SendPrescriptionDialog;
import com.app.drugcorner32.dc_template.Dialogs.ZoomDialog;
import com.app.drugcorner32.dc_template.Fragments.AddressFragment;
import com.app.drugcorner32.dc_template.Fragments.BuyMedicineFragment;
import com.app.drugcorner32.dc_template.Fragments.HomeScreenFragment;
import com.app.drugcorner32.dc_template.Fragments.OrderItemListFragment;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//TODO OrderDetails is dummy data

public class MainActivity extends ActionBarActivity implements OnFragmentChange, HomeScreenFragment.Callback,
        SendPrescriptionDialog.Callback,SearchMedicineDialog.Callback,OrderListAdapter.Callback,BuyMedicineFragment.Callback,
        OrderItemListAdapter.Callback{

    private Uri fileUri;
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private OrderListAdapter orderListAdapter;
    private OrderDetails orderDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        TextView toolbarText = (TextView)findViewById(R.id.toolbarText);

        Typeface typeFace=Typeface.createFromAsset(toolbar.getContext().getAssets(), "fonts/gothic.ttf");
        toolbarText.setTypeface(typeFace);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        com.app.drugcorner32.dc_template.Helpers.helperIDGenerator.init();

        //setting the home fragment
        replaceFragment(R.id.homeScreenImageButton1,null);

        orderListAdapter = new OrderListAdapter(this,R.layout.cards_order);

        //Dummy Data being prepared
        List<OrderItemDetails> itemDetailses = new ArrayList<>();
        PrescriptionDetails prescriptionDetails1 = new PrescriptionDetails();

        prescriptionDetails1.addMedicine(new MedicineDetails("Disprin", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails1.addMedicine(new MedicineDetails("Vino 200ml", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails1.addMedicine(new MedicineDetails("Saradon", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails1.addMedicine(new MedicineDetails("Vicks Action 500", MedicineDetails.MedicineTypes.Tablets,42));

        PrescriptionDetails prescriptionDetails2 = new PrescriptionDetails();

        prescriptionDetails2.addMedicine(new MedicineDetails("Paracitamol", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails2.addMedicine(new MedicineDetails("Calpol", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails2.addMedicine(new MedicineDetails("Anacin", MedicineDetails.MedicineTypes.Tablets, 42));

        itemDetailses.add(new OrderItemDetails(prescriptionDetails1));
        itemDetailses.add(new OrderItemDetails(prescriptionDetails2));

        itemDetailses.add(new OrderItemDetails(new MedicineDetails("Crocin 250mg",MedicineDetails.MedicineTypes.Tablets,42),false));
        itemDetailses.add(new OrderItemDetails(new MedicineDetails("Sumo 100mg",MedicineDetails.MedicineTypes.Tablets,32),false));

        orderDetails = new OrderDetails(1030,2000f,"A - 1002 PRERNA TOWER VASTRAPUR",
                new StatusDetails(StatusDetails.STATUSES.DELIVERED),Calendar.getInstance().getTime(),itemDetailses);

        orderListAdapter.add(orderDetails);
        orderListAdapter.add(orderDetails);
        orderListAdapter.add(orderDetails);
        orderListAdapter.add(orderDetails);
        orderListAdapter.add(orderDetails);
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
            fileUri = Uri.fromFile(imageFile);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            replaceFragment(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE, null);
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

            case R.id.checkoutButton:
                AddressFragment addressFragment = (AddressFragment) getSupportFragmentManager().
                        findFragmentByTag(AddressFragment.TAG);
                if (addressFragment == null) {
                    addressFragment = AddressFragment.newInstance();
                }
                ft.replace(R.id.mainActivityFrameLayout, addressFragment, AddressFragment.TAG);
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
                    break;
                }

                frag3.addNewPrescription(fileUri);
                frag3.updateBottomMenu();
                break;

            case R.id.prescriptionCardImageView1:
                ZoomDialog zoomDialog = new ZoomDialog();
                zoomDialog.setImageUri((Uri) object);
                zoomDialog.show(getSupportFragmentManager(), ZoomDialog.TAG);
                break;

            case R.id.searchMedicineSearchView1:
                BuyMedicineFragment frag4 = (BuyMedicineFragment) getSupportFragmentManager().
                        findFragmentByTag(BuyMedicineFragment.TAG);

                if (frag4 == null) {
                    Toast.makeText(this, "State Loss", Toast.LENGTH_SHORT).show();
                    break;
                }
                frag4.addNewMedicine(new MedicineDetails((String) object, MedicineDetails.MedicineTypes.Bottles));
                frag4.updateBottomMenu();
                break;

            case R.id.orderCardTextView6:
                PreviousOrderDialog previousOrderDialog1 = (PreviousOrderDialog)
                        getSupportFragmentManager().findFragmentByTag(PreviousOrderDialog.TAG);

                if (previousOrderDialog1.isVisible()) {
                    OrderItemListFragment frag5 = (OrderItemListFragment)
                            previousOrderDialog1.getChildFragmentManager().findFragmentByTag(OrderItemListFragment.TAG);

                    if (frag5 == null)
                        frag5 = OrderItemListFragment.newInstance();

                    frag5.setRemovable(false);
                    frag5.setEditable(true);
                    frag5.setOrderDetails((OrderDetails) object);
                    previousOrderDialog1.setSelectedOrderDetails((OrderDetails) object);
                    previousOrderDialog1.changeFragment(frag5);
                }
                break;

            /*case R.id.previousOrderDialogButton1:
                BuyMedicineFragment frag6 = (BuyMedicineFragment) getSupportFragmentManager().
                        findFragmentByTag(BuyMedicineFragment.TAG);

                if (frag6 == null) {
                    Toast.makeText(this, "State Loss", Toast.LENGTH_SHORT).show();
                }

                frag6.addNewPreviousOrders((List<OrderDetails>) object);

                break;*/
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

    public OrderListAdapter getOrder(){
        return orderListAdapter;
    }

    public void startNotificationActivity(){
        Intent intent = new Intent(this,NotificationActivity.class);
        intent.putExtra("Order",orderDetails);
        startActivity(intent);
    }

    public void startAddressActivity(OrderDetails details){
        Intent intent = new Intent(this,AddressActivity.class);
        Toast.makeText(this,details.getOrderItemsList().size() + "",Toast.LENGTH_SHORT).show();
        intent.putExtra("Order",details);
        startActivity(intent);
    }

    public void updateCart(OrderItemDetails details){
        PreviousOrderDialog previousOrderDialog = (PreviousOrderDialog) getSupportFragmentManager().
                findFragmentByTag(PreviousOrderDialog.TAG);
        if (previousOrderDialog != null)
            previousOrderDialog.updateCart(details);

        BuyMedicineFragment buyMedicineFragment = (BuyMedicineFragment)getSupportFragmentManager().
                findFragmentByTag(BuyMedicineFragment.TAG);
        if(buyMedicineFragment!=null) {
            buyMedicineFragment.updateCartAdapter();
        }
    }

    public void updateBottomMenu(){
        BuyMedicineFragment buyMedicineFragment = (BuyMedicineFragment)getSupportFragmentManager().
                findFragmentByTag(BuyMedicineFragment.TAG);
        if(buyMedicineFragment!=null) {
            buyMedicineFragment.updateBottomMenu();
        }
    }

    public void repeatOrder(OrderDetails details){
        PreviousOrderDialog previousOrderDialog = (PreviousOrderDialog) getSupportFragmentManager().
                findFragmentByTag(PreviousOrderDialog.TAG);
        if(previousOrderDialog != null)
            previousOrderDialog.dismiss();

        BuyMedicineFragment buyMedicineFragment = (BuyMedicineFragment)getSupportFragmentManager().
                findFragmentByTag(BuyMedicineFragment.TAG);
        if(buyMedicineFragment!=null) {
            buyMedicineFragment.repeatOrder(details);
            buyMedicineFragment.updateBottomMenu();
        }

    }

}
