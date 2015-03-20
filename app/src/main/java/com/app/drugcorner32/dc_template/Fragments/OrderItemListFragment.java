package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.drugcorner32.dc_template.Adapters.OrderItemListAdapter;
import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnDetectScrollListener;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

import java.util.List;

public class OrderItemListFragment extends android.support.v4.app.Fragment {

    public static String TAG = "OrderItemList";
    private boolean isSelectable = false;
    private boolean isEditable = false;
    private Callbacks callback;

    private OnFragmentInteractionListener mListener;
    private OrderItemListAdapter itemAdapter;
    private OrderDetails orderDetails;

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

        if(getParentFragment()!=null) {
            Bundle bundle = getParentFragment().getArguments();
            if (bundle != null) {
                if (bundle.get("TAG") == BuyMedicineFragment.TAG)
                    callback = (Callbacks)getParentFragment();
            }
        }

        itemAdapter = new OrderItemListAdapter(getActivity());

        /*This makes the fragment reusable with selecting
        from previous order and displaying the current order*/
        if(orderDetails!=null)
            itemAdapter.setItemDetailsList(orderDetails.getOrderItemsList());

        itemAdapter.setSelectable(isSelectable);
        itemAdapter.setEditable(isEditable);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_item_list, container, false);
        final com.app.drugcorner32.dc_template.CustomViews.ListView itemListView =
                (com.app.drugcorner32.dc_template.CustomViews.ListView)view.findViewById(R.id.orderItemListView);

        itemListView.setPadding(0,paddingTop,0,0);

        itemListView.setOnDetectScrollListener(new OnDetectScrollListener() {
            @Override
            public void onUpScrolling() {
                if(callback!=null)
                    callback.showView();
            }

            @Override
            public void onDownScrolling() {
                if(callback!=null)
                    callback.hideView();
            }
        });

        itemListView.setAdapter(itemAdapter);

        return view;
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



    public List<OrderItemDetails> getItemDetailses(){
        return itemAdapter.getItemDetailsList();
    }

    public void setOrderDetails(OrderDetails details){
        orderDetails = details;
    }

    public void addOrderItem(OrderItemDetails details){
        itemAdapter.add(details);
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public int countAndTell(){
        int totalCount = 0;
        int count = 0;
        for(int i = 0 ;i<itemAdapter.getCount();i++){
            OrderItemDetails details = itemAdapter.getItem(i);
            if(details.getOrderType()== OrderItemDetails.TypesOfOrder.TRANSLATED_PRESCRIPTION){
                totalCount+=details.getPrescriptionDetails().getMedicineList().size();
                if(details.getSelected())
                    count+=details.getPrescriptionDetails().getMedicineList().size();
                else
                    for(MedicineDetails details1 : details.getPrescriptionDetails().getMedicineList())
                        if(details1.getSelection())
                            count++;
            }
            else{
                totalCount++;
                if(details.getSelected())
                    count++;
            }
        }
        if(totalCount == count)
            return 2;
        else if(count == 0)
            return 0;
        else
            return 1;
    }

    public void setSelectable(boolean val){
        isSelectable = val;
    }

    public void setEditable(boolean val){
        isEditable = val;
    }

    public void addPreviousOrders(List<OrderDetails> orderDetailses){
        //Selection process : the selected medicines are being added to the cart
        for(OrderDetails details : orderDetailses)
            for(OrderItemDetails itemDetails : details.getOrderItemsList()) {
                if(itemDetails.getSelected())
                    itemAdapter.add(new OrderItemDetails(itemDetails, true));
            }
        itemAdapter.notifyDataSetChanged();
    }

    public void setListViewPaddingTop(int val){
        paddingTop = val + 50;
    }

    public static interface Callbacks{
        public void hideView();
        public void showView();
    }

}