package com.app.drugcorner32.dc_template.Dialogs;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.R;


/**
 * Created by Tarun on 27-02-2015.
 * This dialog gives the options whether to click the prescription from camera or from gallery
 */
public class SendPrescriptionDialog extends DialogFragment {

    ImageButton cameraButton;
    ImageButton galleryButton;
    TextView cameraTextView;
    TextView galleryTextView;

    Callback callBack;

    public static String TAG = "SendPrescriptionDialog";

    public SendPrescriptionDialog(){
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callBack = (Callback)getActivity();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void onStart() {


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_send_prescripiton,null);

        galleryTextView = (TextView)view.findViewById((R.id.galleryTextView));
        cameraTextView = (TextView)view.findViewById((R.id.cameraTextView));

        cameraButton = (ImageButton)view.findViewById(R.id.cameraButton);
        galleryButton = (ImageButton)view.findViewById(R.id.galleryButton);

        //Assign custom fonts to TextView
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/gothic.ttf");
        galleryTextView.setTypeface(typeFace);
        cameraTextView.setTypeface(typeFace);

        //Events of Buttons
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.takePhoto();
                dismiss();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    public static interface Callback{
        public void takePhoto();
    }
}
