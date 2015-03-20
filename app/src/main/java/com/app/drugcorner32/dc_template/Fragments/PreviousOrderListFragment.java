package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.drugcorner32.dc_template.Adapters.OrderListAdapter;
import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Data.Status;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
TODO make the selection more efficient , in the confirm button
 */

public class PreviousOrderListFragment extends android.support.v4.app.Fragment {

    public static String TAG = "PreviousOrderList";

    private int currentSelectedOrder = -1;

    private boolean isSelectable = false;

    private List<OrderDetails> selectedOrders;

    private OrderListAdapter orderListAdapter;
    private OnFragmentInteractionListener mListener;

    public static PreviousOrderListFragment newInstance() {
        PreviousOrderListFragment fragment = new PreviousOrderListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public PreviousOrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        selectedOrders = new ArrayList<>();
        orderListAdapter = new OrderListAdapter(getActivity(), R.layout.cards_order);
        //Dummy Data being prepared
        List<OrderItemDetails> itemDetailses = new ArrayList<>();
        PrescriptionDetails prescriptionDetails1 = new PrescriptionDetails();

        prescriptionDetails1.getMedicineList().add(new MedicineDetails("DD", MedicineDetails.MedicineTypes.Tablets, 42));
        prescriptionDetails1.getMedicineList().add(new MedicineDetails("DD", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails1.getMedicineList().add(new MedicineDetails("DD", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails1.getMedicineList().add(new MedicineDetails("DD", MedicineDetails.MedicineTypes.Tablets,42));

        PrescriptionDetails prescriptionDetails2 = new PrescriptionDetails();

        prescriptionDetails2.getMedicineList().add(new MedicineDetails("FD", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails2.getMedicineList().add(new MedicineDetails("FFD", MedicineDetails.MedicineTypes.Tablets,42));
        prescriptionDetails2.getMedicineList().add(new MedicineDetails("FD", MedicineDetails.MedicineTypes.Tablets, 42));

        itemDetailses.add(new OrderItemDetails(OrderItemDetails.TypesOfOrder.TRANSLATED_PRESCRIPTION,prescriptionDetails1,false));
        itemDetailses.add(new OrderItemDetails(OrderItemDetails.TypesOfOrder.TRANSLATED_PRESCRIPTION,prescriptionDetails2,false));

        itemDetailses.add(new OrderItemDetails(new MedicineDetails("AA",MedicineDetails.MedicineTypes.Tablets,42),false));
        itemDetailses.add(new OrderItemDetails(new MedicineDetails("BA",MedicineDetails.MedicineTypes.Tablets,32),false));

        OrderDetails orderDetails = new OrderDetails(1030,2000f,"A - 1002 PRERNA TOWER VASTRAPUR",
                new Status(Status.STATUSES.DELIVERED),Calendar.DATE,itemDetailses);

        orderListAdapter.add(orderDetails);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_order_list, container, false);

        ListView previousOrderListView = (ListView) view.findViewById(R.id.previousOrderListView);
        orderListAdapter.setSelectable(isSelectable);
        previousOrderListView.setAdapter(orderListAdapter);

        //Events
        previousOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSelectedOrder = position;
                 mListener.replaceFragment(R.id.previousOrderListView, orderListAdapter.getItem(position));
                }
        });
        return view;
    }

    /* This method is used to unselect an order which has been selected but
    individual prescriptions and medicines have been modified or unselected
    i.e. Primarily Selecting an order selects all the items inside of it
    But as soon as those items are unslelected individually this unchecks the select all of the order */

    public void selectCurrentOrder(boolean val){
        orderListAdapter.getItem(currentSelectedOrder).setSelection(val);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setSelectable(boolean val){
        isSelectable = val;
    }

    public List<OrderDetails> getSelectedOrdersList(){
        return selectedOrders;
    }

}
