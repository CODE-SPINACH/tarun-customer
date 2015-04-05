package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.R;


public class HomeScreenFragment extends android.support.v4.app.Fragment {

    public static String TAG = "Home";

    private Callback callback;

    public static HomeScreenFragment newInstance() {
        HomeScreenFragment fragment = new HomeScreenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeScreenFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        ImageButton buyMedicineButton = (ImageButton)view.findViewById(R.id.homeScreenImageButton1);
        ImageButton callUsButton = (ImageButton)view.findViewById(R.id.homeScreenImageButton2);

        TextView buyMedicine=(TextView)view.findViewById(R.id.homeScreenTextView1);
        TextView callUs=(TextView)view.findViewById(R.id.homeScreenTextView2);

        //Applying custom font to the views
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/gothic.ttf");
        buyMedicine.setTypeface(typeFace);
        callUs.setTypeface(typeFace);

        //Events on Buttons
        buyMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            callback.replaceFragment(R.id.homeScreenImageButton1, null);
            }
        });

        callUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            callback.startNotificationActivity();
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

    public static interface Callback{
        public void replaceFragment(int id ,Object o);
        public void startNotificationActivity();
    }

}
