package com.app.drugcorner32.dc_template.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
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
import java.io.FileOutputStream;
import java.util.Calendar;

public class NotificationActivity extends ActionBarActivity implements OnFragmentChange, OrderItemListAdapter.Callback,
        SendPrescriptionDialog.Callback{

    private OrderDetails orderDetails;
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private final int GALLERY_IMAGE_ACTIVITY_REQUEST_CODE = 101;

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

            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                ResendImageDialog resendImageDialog1 = (ResendImageDialog)
                        getSupportFragmentManager().findFragmentByTag(ResendImageDialog.TAG);

                Calendar calendar = Calendar.getInstance();
                File dir = getPicStorageDir("prescription_images_thumbnails");
                File thumbnailFile = new File(dir, calendar.getTimeInMillis() + ".jpeg");
                Uri thumbnailUri = Uri.fromFile(thumbnailFile);

                File imageFile = new File(fileUri.getPath());

                Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imageFile.getPath()),
                        100, 100);
                try {
                    FileOutputStream out = new FileOutputStream(thumbnailFile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                resendImageDialog1.replaceCurrentPhoto(fileUri,thumbnailUri);

                break;
            case GALLERY_IMAGE_ACTIVITY_REQUEST_CODE:
                ResendImageDialog resendImageDialog2 = (ResendImageDialog)
                        getSupportFragmentManager().findFragmentByTag(ResendImageDialog.TAG);

                String path = getRealPathFromURI(this,fileUri);

                Calendar calendar1 = Calendar.getInstance();
                File dir1 = getPicStorageDir("prescription_images_thumbnails");
                File thumbnailFile1 = new File(dir1, calendar1.getTimeInMillis() + ".jpeg");
                File imageFile1 = new File(path);
                Uri thumbnailUri1 = Uri.fromFile(thumbnailFile1);
                Bitmap bitmap1 = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(imageFile1.getAbsolutePath()),
                        100, 100);
                try {
                    FileOutputStream out = new FileOutputStream(thumbnailFile1);
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resendImageDialog2.replaceCurrentPhoto(fileUri,thumbnailUri1);
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
            fileUri = Uri.fromFile(imageFile);
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public void choosePhotoFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            replaceFragment(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE, null);
        }
        else if(requestCode == GALLERY_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                fileUri = data.getData();
                replaceFragment(GALLERY_IMAGE_ACTIVITY_REQUEST_CODE,null);
            }
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

    public void updateBottomMenu(){
        EditMedicineDialog editMedicineDialog = (EditMedicineDialog)
                getSupportFragmentManager().findFragmentByTag(EditMedicineDialog.TAG);
        if(editMedicineDialog == null)
            return;
        editMedicineDialog.updateBottomMenu();
    }

    public void startTimer(){
        new CountDownTimer(60*1000, 1000) {

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

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
