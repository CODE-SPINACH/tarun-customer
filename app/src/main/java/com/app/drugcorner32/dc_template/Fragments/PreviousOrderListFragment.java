package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.drugcorner32.dc_template.Activities.MainActivity;
import com.app.drugcorner32.dc_template.Adapters.OrderListAdapter;
import com.app.drugcorner32.dc_template.R;

/*
TODO make the selection more efficient , in the confirm button
 */

public class PreviousOrderListFragment extends android.support.v4.app.Fragment {

    public static String TAG = "PreviousOrderList";

    private int currentSelectedOrder = -1;

    private boolean isSelectable = false;

    private OrderListAdapter orderListAdapter;
    private Callback callback;

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
        orderListAdapter = ((MainActivity)getActivity()).getOrder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_order_list, container, false);

        ListView previousOrderListView = (ListView) view.findViewById(R.id.previousOrderListView);
        orderListAdapter.setSelectable(isSelectable);
        previousOrderListView.setAdapter(orderListAdapter);

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

    public void setSelectable(boolean val){
        isSelectable = val;
    }

    public static interface Callback{
        public void replaceFragment(int id , Object o);
    }

}
