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
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Dialogs.SendPrescriptionDialog;
import com.app.drugcorner32.dc_template.Fragments.NotificationFragment;
import com.app.drugcorner32.dc_template.Fragments.OrderItemListFragment;
import com.app.drugcorner32.dc_template.Fragments.ResendImageFragment;
import com.app.drugcorner32.dc_template.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class NotificationActivity extends ActionBarActivity implements OrderItemListFragment.Callback,
        NotificationFragment.Callback,OrderItemListAdapter.Callback,ResendImageFragment.Callback,SendPrescriptionDialog.Callback{
    private OrderDetails orderDetails;
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.notificationToolBar);
        TextView toolbarText = (TextView)findViewById(R.id.notificationToolbarText);

        Typeface typeFace=Typeface.createFromAsset(toolbar.getContext().getAssets(), "fonts/gothic.ttf");
        toolbarText.setTypeface(typeFace);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        orderDetails = (OrderDetails)getIntent().getSerializableExtra("Order");

        if(orderDetails == null)
            Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();

        replaceFragment(R.layout.fragment_notification, null);
    }


    public void replaceFragment(int id,Object object){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (id){

            case R.layout.fragment_notification:
                ft.add(R.id.container,NotificationFragment.newInstance(),NotificationFragment.TAG).commitAllowingStateLoss();
                break;

            case R.id.buttonNotificationCardButton1:
                ResendImageFragment resendImageFragment = ResendImageFragment.newInstance();
                ft.replace(R.id.container,resendImageFragment,ResendImageFragment.TAG).
                        setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                        addToBackStack(null).commitAllowingStateLoss();
                /*NotificationDetails details = (NotificationDetails)object;

                if(details.getType() == NotificationDetails.NOTIFICATION_TYPE.VIEW){
                    OrderItemListFragment itemListFragment = OrderItemListFragment.newInstance();
                    itemListFragment.setOrderDetails(orderDetails);
                    itemListFragment.setEditable(true);

                    ft.replace(R.id.container,itemListFragment,OrderItemListFragment.TAG).
                            setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                            addToBackStack(null).commitAllowingStateLoss();
                }
                else if(details.getType() == NotificationDetails.NOTIFICATION_TYPE.EDIT){

                }
                else{

                }*/
                break;

            case R.id.resendButton1:
                SendPrescriptionDialog sendPrescriptionDialog = new SendPrescriptionDialog();
                sendPrescriptionDialog.show(getSupportFragmentManager(),SendPrescriptionDialog.TAG);
                break;

            default:
                break;
        }
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

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void updateCart(OrderItemDetails details){

    }

    public void updateBottomMenu(){

    }

}
