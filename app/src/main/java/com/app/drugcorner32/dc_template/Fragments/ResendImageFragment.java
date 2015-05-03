package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;

public class ResendImageFragment extends android.support.v4.app.Fragment {
    private Callback callback;

    public static String TAG = "ResendImage";
    private List<PrescriptionDetails> prescriptionDetailsList = new ArrayList<>();

    private RelativeLayout previousSelectedLayout;
    private PrescriptionDetails previousSelectedDetails;

    //This is the currently selected image position
    private int currentSelectionPosition;

    public static ResendImageFragment newInstance() {
        ResendImageFragment fragment = new ResendImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ResendImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_resend_image, container, false);

        final HorizontalScrollView scrollView = (HorizontalScrollView)view.findViewById(R.id.resendHorizontalScrollView1);
        final LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.resendLinearLayout1);
        final ImageView image = (ImageView)view.findViewById(R.id.resendImageView1);
        Button resendButton = (Button)view.findViewById(R.id.resendButton1);
        Button cancelButton = (Button)view.findViewById(R.id.resendButton2);

       for(int i = 0;i<prescriptionDetailsList.size();i++){
           final PrescriptionDetails prescriptionDetails = prescriptionDetailsList.get(i);
           final RelativeLayout relativeLayout = (RelativeLayout)inflater.inflate(R.layout.layout_resend_thumbnail,(LinearLayout)view,false);
           ImageView thumbnailView = (ImageView)relativeLayout.findViewById(R.id.resendThumbnailImageView1);
           ImageView imageStatusView = (ImageView)relativeLayout.findViewById(R.id.resendThumbnailImageView2);

           int thumbnailSize = (int)getResources().getDimension(R.dimen.thumbnail_size);
           //thumbnailView.setImageBitmap(HelperClass.getPreview(prescriptionDetails.getDays(), thumbnailSize, getActivity()));
           linearLayout.addView(relativeLayout);

           relativeLayout.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(previousSelectedLayout != null)
                       previousSelectedLayout.setBackgroundColor(Color.parseColor("#777777"));

                   //The below written code lets the scrolled out portion to be completely visible on selection
                   int screenRelativeRight = relativeLayout.getRight() - scrollView.getScrollX();
                   int screenRelativeLeft = relativeLayout.getLeft() - scrollView.getScrollX();

                   if(screenRelativeRight > view.getRight())
                       scrollView.scrollBy(screenRelativeRight - view.getRight(),0);
                   else if(screenRelativeLeft < view.getLeft())
                       scrollView.scrollBy(screenRelativeLeft - view.getLeft(),0);

                   relativeLayout.setBackgroundColor(Color.parseColor("#ea6125"));
//                   image.setImageBitmap(HelperClass.getPreview(prescriptionDetails.getImageUri(),
  //                         image.getWidth(), getActivity()));

                   previousSelectedDetails = prescriptionDetails;
                   previousSelectedLayout = relativeLayout;
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
                if(previousSelectedDetails != null) {
                    prescriptionDetailsList.remove(previousSelectedDetails);
                    linearLayout.removeView(previousSelectedLayout);
                    image.setImageBitmap(null);

                    previousSelectedDetails = null;
                    previousSelectedLayout = null;
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    public void setPrescriptionDetailsList(List<PrescriptionDetails> list){
        prescriptionDetailsList = list;
    }

    public void replaceCurrentPhoto(Uri newURI){
        previousSelectedDetails.setImageUri(newURI);
    }

    public static interface Callback {
        public void replaceFragment(int id, Object object);
    }

}
