package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Interfaces.NotificationFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

public class NotificationFragment extends android.support.v4.app.Fragment {
    private NotificationFragmentInteractionListener callback;
    private OrderDetails orderDetails;
    public static String TAG = "Notification";

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NotificationFragment() {
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (NotificationFragmentInteractionListener) activity;
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

    public void setOrderDetails(OrderDetails details){
        orderDetails = details;
    }
}
