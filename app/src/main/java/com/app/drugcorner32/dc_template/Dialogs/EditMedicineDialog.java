package com.app.drugcorner32.dc_template.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Fragments.OrderItemListFragment;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

/**
 * Created by Tarun on 05-05-2015.
 */
public class EditMedicineDialog extends DialogFragment {

    public static String TAG = "Edit Medicine";
    private OrderDetails orderDetails;
    private OnFragmentChange callback;
    private TextView counterTextView;

    public EditMedicineDialog(){
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
        final View view = inflater.inflate(R.layout.dialog_edit_medicine, container, false);

        ImageButton closeButton = (ImageButton)view.findViewById(R.id.editMedicinesToolbarImageButton1);
        counterTextView = (TextView)view.findViewById(R.id.editMedicinesTextView1);

        OrderItemListFragment itemListFragment = (OrderItemListFragment) getChildFragmentManager().
                findFragmentByTag(OrderItemListFragment.TAG);

        if(itemListFragment == null) {
            itemListFragment = OrderItemListFragment.newInstance();
        }

        itemListFragment.setOrderDetails(orderDetails);
        itemListFragment.setRemovable(true);
        itemListFragment.setEditable(true);

        getChildFragmentManager().beginTransaction().
                replace(R.id.editMedicinesFrameLayout1, itemListFragment, OrderItemListFragment.TAG).commitAllowingStateLoss();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    public void setOrderDetails(OrderDetails details){
        orderDetails = details;
    }

    public void setTimer(String str){
        counterTextView.setText("Time Left : " + str);
    }
}
