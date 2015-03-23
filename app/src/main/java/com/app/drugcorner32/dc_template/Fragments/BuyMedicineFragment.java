package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

import java.util.List;


public class BuyMedicineFragment extends android.support.v4.app.Fragment {
    private OnFragmentInteractionListener callback;

    public static String TAG = "BuyMedicine";

    private OrderItemListFragment itemListFragment;

    private LinearLayout hidableLayout;

    public static BuyMedicineFragment newInstance() {
        BuyMedicineFragment fragment = new BuyMedicineFragment();
        Bundle args = new Bundle();
        args.putCharSequence("TAG",TAG);
        fragment.setArguments(args);
        return fragment;
    }

    public BuyMedicineFragment() {
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
        View view = inflater.inflate(R.layout.fragment_buy_medicine, container, false);
        TextView prescriptionText = (TextView)view.findViewById(R.id.buyMedicineTextView1);
        TextView manuallyText = (TextView)view.findViewById(R.id.buyMedicineTextView2);
        TextView previousOrderText = (TextView)view.findViewById(R.id.buyMedicineTextView3);
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/gothic.ttf");

        prescriptionText.setTypeface(typeFace);
        manuallyText.setTypeface(typeFace);
        previousOrderText.setTypeface(typeFace);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.buyMedicineLinearLayout1);
        hidableLayout = (LinearLayout)view.findViewById(R.id.buyMedicineLinearLayout2);

        LinearLayout sendPrescriptionLayout = (LinearLayout)view.findViewById(R.id.buyMedicineLinearLayout2);
        LinearLayout enterManuallyLayout = (LinearLayout)view.findViewById(R.id.buyMedicineLinearLayout3);
        LinearLayout previousOrderLayout = (LinearLayout)view.findViewById(R.id.buyMedicineLinearLayout4);

        itemListFragment = (OrderItemListFragment)
                getChildFragmentManager().findFragmentByTag(OrderItemListFragment.TAG);

        if(itemListFragment == null)
            itemListFragment = OrderItemListFragment.newInstance();


        itemListFragment.setEditable(true);
        itemListFragment.setListViewPaddingTop(linearLayout.getLayoutParams().height +
                hidableLayout.getLayoutParams().height);


        android.support.v4.app.FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.buyMedicineListViewContainer,itemListFragment,OrderItemListFragment.TAG).commitAllowingStateLoss();

        //Events on buttons
        sendPrescriptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.replaceFragment(R.layout.dialog_send_prescripiton, null);
            }
        });

        enterManuallyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.replaceFragment(R.layout.dialog_search_medicine, null);
            }
        });

        //Here the current order list is being forwarded to the Previous Order Dialog
        //This is being done to prevent redundant entries from being entered from the previous order
        previousOrderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.replaceFragment(R.layout.dialog_previous_order, itemListFragment.getItemDetailses());
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (OnFragmentInteractionListener) activity;
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

    public void addNewPrescription(Uri imagePath){
        itemListFragment.addOrderItem(new OrderItemDetails
                (OrderItemDetails.TypesOfOrder.UNTRANSLATED_PRESCRIPTION,
                        new PrescriptionDetails(imagePath, null,getActivity()),true));
    }

    public void addNewMedicine(MedicineDetails medicineDetails){
        itemListFragment.addOrderItem(new OrderItemDetails(medicineDetails,true));
    }

    public void addNewPreviousOrders(List<OrderDetails> orderDetailses){
        itemListFragment.addPreviousOrders(orderDetailses);
    }


}
