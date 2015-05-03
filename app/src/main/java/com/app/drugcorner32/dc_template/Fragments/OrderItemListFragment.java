package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.drugcorner32.dc_template.Adapters.OrderItemListAdapter;
import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.R;

import java.util.List;

public class OrderItemListFragment extends android.support.v4.app.Fragment {

    public static String TAG = "OrderItemList";
    private boolean isSelectable = false;
    private boolean isEditable = false;

    private Callback callback;
    private OrderItemListAdapter itemAdapter;
    private OrderDetails orderDetails;
    private ExpandableListView itemListView;

    private int paddingTop = 0;

    public static OrderItemListFragment newInstance() {
        OrderItemListFragment fragment = new OrderItemListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public OrderItemListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        itemAdapter = new OrderItemListAdapter(getActivity());

        /*This makes the fragment reusable with selecting
        from previous order and displaying the current order*/
        if(orderDetails!=null)
            itemAdapter.setItemDetailsList(orderDetails.getOrderItemsList());
        itemAdapter.setSelectable(isSelectable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_item_list, container, false);
        itemListView =
                (ExpandableListView)view.findViewById(R.id.orderItemListView);
        itemAdapter.setExpandableListView(itemListView);
        itemListView.setAdapter(itemAdapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Callback");
        }
    }



    public List<OrderItemDetails> getItemDetailses(){
        return itemAdapter.getItemDetailsList();
    }

    public void setOrderDetails(OrderDetails details){
        orderDetails = details;
    }

    public void addOrderItem(OrderItemDetails details){
        itemAdapter.add(details);
        itemAdapter.notifyDataSetChanged();
        if(itemListView!=null) {
            itemListView.post(new Runnable(){
                public void run() {
                    itemListView.smoothScrollToPosition(itemListView.getCount() - 1);
                }});
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }


    public void setSelectable(boolean val){
        isSelectable = val;
    }

    public void setEditable(boolean val){
        isEditable = val;
    }

    public void addPreviousOrder(OrderDetails orderDetails) {
        List<OrderItemDetails> cartItemList = itemAdapter.getItemDetailsList();
        for (OrderItemDetails details : orderDetails.getOrderItemsList()) {
            cartItemList.remove(details);
            if (details.getOrderType() == OrderItemDetails.TypesOfOrder.PRESCRIPTION) {
                if(details.getPrescriptionDetails().getPrescriptionType() == PrescriptionDetails.TypesOfPrescription.TRANSLATED_PRESCRIPTION)
                    for (MedicineDetails medicineDetails : details.getPrescriptionDetails().getMedicineList())
                        medicineDetails.setQuantity(medicineDetails.getPreviousQuantity());
            } else
                details.getMedicineDetails().setQuantity(details.getMedicineDetails().getPreviousQuantity());
            cartItemList.add(details);
        }
        itemAdapter.notifyDataSetChanged();
    }


    public void updateCartAdapter(){
        if(itemAdapter!=null)
            itemAdapter.notifyDataSetChanged();
    }

    public static interface Callback{
        public void replaceFragment(int id, Object object);
    }

}
