package com.app.drugcorner32.dc_template.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarun on 05-05-2015.
 *
 */
public class ResendImageDialog extends android.support.v4.app.DialogFragment{

    public static String TAG = "ResendImage";

    private OnFragmentChange callback;
    private List<PrescriptionDetails> prescriptionDetailsList = new ArrayList<>();

    private RelativeLayout currentlySelectedLayout;
    private PrescriptionDetails currentlySelectedPrescription;
    private ImageView image;

    public ResendImageDialog(){
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = (OnFragmentChange) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_resend_image, container, false);

        ImageButton toolbarBackButton = (ImageButton)view.findViewById(R.id.resendImageToolbarImageButton1);
        TextView toolbarTitleView = (TextView) view.findViewById(R.id.resendImageToolbarTextView1);

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/gothic.ttf");
        toolbarTitleView.setTypeface(typeFace);

        final HorizontalScrollView scrollView = (HorizontalScrollView)view.findViewById(R.id.resendHorizontalScrollView1);
        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.resendLinearLayout1);
        image = (ImageView)view.findViewById(R.id.resendImageView1);
        Button resendButton = (Button)view.findViewById(R.id.resendButton1);
        Button cancelButton = (Button)view.findViewById(R.id.resendButton2);

        resendButton.setTypeface(typeFace);
        cancelButton.setTypeface(typeFace);

        for(int i = 0;i<prescriptionDetailsList.size();i++){
            final PrescriptionDetails prescriptionDetails = prescriptionDetailsList.get(i);
            final RelativeLayout relativeLayout = (RelativeLayout)inflater.inflate(R.layout.layout_resend_thumbnail, (RelativeLayout) view, false);
            ImageView thumbnailView = (ImageView)relativeLayout.findViewById(R.id.resendThumbnailImageView1);
            ImageView imageStatusView = (ImageView)relativeLayout.findViewById(R.id.resendThumbnailImageView2);

            thumbnailView.setImageURI(prescriptionDetails.getThumbnailUri());
            linearLayout.addView(relativeLayout);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentlySelectedLayout != null)
                        currentlySelectedLayout.setBackgroundColor(Color.parseColor("#eeeeee"));

                    //The below written code lets the scrolled out portion to be completely visible on selection
                    int screenRelativeRight = relativeLayout.getRight() - scrollView.getScrollX();
                    int screenRelativeLeft = relativeLayout.getLeft() - scrollView.getScrollX();

                    if(screenRelativeRight > view.getRight())
                        scrollView.smoothScrollBy(screenRelativeRight - view.getRight(), 0);
                    else if(screenRelativeLeft < view.getLeft())
                        scrollView.smoothScrollBy(screenRelativeLeft - view.getLeft(), 0);

                    relativeLayout.setBackgroundColor(Color.parseColor("#36B666"));
                    image.setImageURI(prescriptionDetails.getImageUri());

                    currentlySelectedPrescription = prescriptionDetails;
                    currentlySelectedLayout = relativeLayout;
                }
            });
        }
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.replaceFragment(R.id.resendButton1,null);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentlySelectedPrescription != null) {
                    prescriptionDetailsList.remove(currentlySelectedPrescription);
                    linearLayout.removeView(currentlySelectedLayout);
                    image.setImageBitmap(null);

                    currentlySelectedPrescription = null;
                    currentlySelectedLayout = null;
                }
            }
        });

        toolbarBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void setPrescriptionList(List<OrderItemDetails> list) {
        for (OrderItemDetails details : list)
            if (details.getOrderType() == OrderItemDetails.TypesOfOrder.PRESCRIPTION)
                if (details.getPrescriptionDetails().getPrescriptionType() ==
                        PrescriptionDetails.TypesOfPrescription.UNTRANSLATED_PRESCRIPTION)
                    prescriptionDetailsList.add(details.getPrescriptionDetails());
    }

    public void replaceCurrentPhoto(Uri imageUri,Uri thumbnailUri){
        currentlySelectedPrescription.setThumbnailUri(thumbnailUri);
        currentlySelectedPrescription.setImageUri(imageUri);

        ImageView thumbnailView = (ImageView)currentlySelectedLayout.findViewById(R.id.resendThumbnailImageView1);
        ImageView imageStatusView = (ImageView)currentlySelectedLayout.findViewById(R.id.resendThumbnailImageView2);

        thumbnailView.setImageURI(currentlySelectedPrescription.getThumbnailUri());
        image.setImageURI(currentlySelectedPrescription.getImageUri());
    }

}
