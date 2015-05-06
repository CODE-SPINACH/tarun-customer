package com.app.drugcorner32.dc_template.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drugcorner32.dc_template.Adapters.OrderItemListAdapter;
import com.app.drugcorner32.dc_template.Data.NotificationDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.StatusDetails;
import com.app.drugcorner32.dc_template.Dialogs.EditMedicineDialog;
import com.app.drugcorner32.dc_template.Dialogs.ResendImageDialog;
import com.app.drugcorner32.dc_template.Dialogs.SendPrescriptionDialog;
import com.app.drugcorner32.dc_template.Dialogs.ViewMedicineDialog;
import com.app.drugcorner32.dc_template.Fragments.NotificationFragment;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class NotificationActivity extends ActionBarActivity implements OnFragmentChange, OrderItemListAdapter.Callback{

    private OrderDetails orderDetails;
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ImageButton backButton = (ImageButton) findViewById(R.id.notificationToolbarImageButton1);
        Button button = (Button) findViewById(R.id.button1);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.notificationToolBar);
        TextView toolbarText = (TextView)findViewById(R.id.notificationToolbarTextView1);

        Typeface typeFace=Typeface.createFromAsset(toolbar.getContext().getAssets(), "fonts/gothic.ttf");
        toolbarText.setTypeface(typeFace);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        orderDetails = (OrderDetails)getIntent().getSerializableExtra("Order");

        if(orderDetails == null)
            Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();

        replaceFragment(R.layout.fragment_notification, null);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (count){
                    case 0:
                        updateStatus(StatusDetails.STATUSES.BEING_PROCESSED,null);
                        break;
                    case 1:
                        updateStatus(StatusDetails.STATUSES.RESEND_IMAGE,null);
                        break;
                    case 2:
                        updateStatus(StatusDetails.STATUSES.EDIT_MEDICINE,null);
                        break;
                }
                count++;
                count = count % 3;

            }
        });
    }


    public void replaceFragment(int id,Object object){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (id){

            case R.layout.fragment_notification:
                ft.add(R.id.container,NotificationFragment.newInstance(),NotificationFragment.TAG).commitAllowingStateLoss();
                break;

            case R.id.buttonNotificationCardButton1:
                NotificationDetails details = (NotificationDetails)object;

                if(details.getType() == NotificationDetails.NOTIFICATION_TYPE.VIEW){
                    ViewMedicineDialog viewMedicineDialog = new ViewMedicineDialog();
                    viewMedicineDialog.setOrderDetails(orderDetails);
                    viewMedicineDialog.show(getSupportFragmentManager(),ViewMedicineDialog.TAG);
                }
                else if(details.getType() == NotificationDetails.NOTIFICATION_TYPE.EDIT){
                    EditMedicineDialog editMedicineDialog = new EditMedicineDialog();
                    editMedicineDialog.setOrderDetails(orderDetails);
                    editMedicineDialog.show(getSupportFragmentManager(),EditMedicineDialog.TAG);
                }
                else{
                    ResendImageDialog resendImageDialog = new ResendImageDialog();
                    resendImageDialog.setPrescriptionList(orderDetails.getOrderItemsList());
                    resendImageDialog.show(getSupportFragmentManager(),ResendImageDialog.TAG);
                }
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
            replaceFragment(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE, null);
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

    public void updateCart(OrderItemDetails details){}
    public void updateBottomMenu(){}

    public void startTimer(){
        new CountDownTimer(10*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int minutes = (int)(millisUntilFinished / (60 * 1000));
                int seconds = (int)((millisUntilFinished / 1000) % 60);
                String str = String.format("%d:%02d", minutes, seconds - 1);

                NotificationFragment notificationFragment =
                        (NotificationFragment)getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG);
                if(notificationFragment != null){
                    notificationFragment.updateTimer(str, NotificationDetails.NOTIFICATION_TYPE.EDIT);
                }

                EditMedicineDialog editMedicineDialog = (EditMedicineDialog)
                        getSupportFragmentManager().findFragmentByTag(EditMedicineDialog.TAG);

                if(editMedicineDialog != null) {
                    editMedicineDialog.setTimer(str);
                }
            }

            public void onFinish() {
                NotificationFragment notificationFragment =
                        (NotificationFragment)getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG);
                if(notificationFragment != null){
                    notificationFragment.deleteNotificationCard(NotificationDetails.NOTIFICATION_TYPE.EDIT);
                }

                EditMedicineDialog editMedicineDialog = (EditMedicineDialog)
                        getSupportFragmentManager().findFragmentByTag(EditMedicineDialog.TAG);
                if(editMedicineDialog != null)
                    editMedicineDialog.dismiss();
            }
        }.start();

    }

    public void updateStatus(StatusDetails.STATUSES status, NotificationDetails details) {
        NotificationFragment notificationFragment =
                (NotificationFragment) getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG);
        if (notificationFragment == null)
            return;

        switch (status) {
            case BEING_PROCESSED:
                if (orderDetails.getOrderStatus().getCurrentStatus() != status) {
                    orderDetails.getOrderStatus().setCurrentStatus(status);
                    notificationFragment.deleteAllNotifications();
                    notificationFragment.addNewNotification(
                            new NotificationDetails("Order is being processed. \n Please Wait.", NotificationDetails.NOTIFICATION_TYPE.TEXT));
                }
                break;
            case RESEND_IMAGE:
                if (orderDetails.getOrderStatus().getCurrentStatus() != status) {
                    notificationFragment.deleteAllNotifications();
                    notificationFragment.addNewNotification(
                            new NotificationDetails("Resend Bad Image", NotificationDetails.NOTIFICATION_TYPE.CLICK_AGAIN));
                }
                break;
            case EDIT_MEDICINE:
                if (orderDetails.getOrderStatus().getCurrentStatus() != status) {
                    notificationFragment.deleteAllNotifications();
                    notificationFragment.addNewNotification(
                            new NotificationDetails("Your prescriptions have been translated.\nYou may edit your order now.\n" +
                                    "Time Left : ", NotificationDetails.NOTIFICATION_TYPE.EDIT));
                    startTimer();
                }
                break;
            case ON_THE_WAY:
                if (orderDetails.getOrderStatus().getCurrentStatus() != status) {
                    notificationFragment.deleteAllNotifications();
                    notificationFragment.addNewNotification(
                            new NotificationDetails("Your order has been confirmed.\n Estimated Delivery time is :",
                                    NotificationDetails.NOTIFICATION_TYPE.DELIVERY_TIME));
                }
                else{
                    notificationFragment.addNewNotification(details);
                }

                break;
            case DELIVERED:
                break;
            case CANCELLED:
                if (orderDetails.getOrderStatus().getCurrentStatus() != status) {
                    notificationFragment.deleteAllNotifications();
                    notificationFragment.addNewNotification(
                            new NotificationDetails("Order has been cancelled", NotificationDetails.NOTIFICATION_TYPE.TEXT));
                }
                break;
        }
    }

}
