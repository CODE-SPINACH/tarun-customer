package com.app.drugcorner32.dc_template.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.CustomViews.TouchImageView;
import com.app.drugcorner32.dc_template.R;

/**
 * Created by Tarun on 15-03-2015.
 *
 */
public class ZoomDialog extends DialogFragment {

    private Uri fileUri;
    public static String TAG = "ZoomDialog";

    public ZoomDialog() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_zoom_image, container, false);
        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(), "fonts/gothic.ttf");

        TextView titleView = (TextView)view.findViewById(R.id.zoomImageToolbarTextView1);
        TouchImageView touchImageView = (TouchImageView)view.findViewById(R.id.zoomImageView1);
        ImageButton closeButton = (ImageButton)view.findViewById(R.id.zoomImageToolbarImageButton1);

        titleView.setTypeface(typeface);
        touchImageView.setImageURI(fileUri);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public void setImageUri(Uri imageUri){
        fileUri = imageUri;
    }

}
