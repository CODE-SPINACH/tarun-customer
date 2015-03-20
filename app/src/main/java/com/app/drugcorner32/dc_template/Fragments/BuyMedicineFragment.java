package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

import java.util.List;


public class BuyMedicineFragment extends android.support.v4.app.Fragment implements OrderItemListFragment.Callbacks {
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
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.buyMedicineLinearLayout1);
        hidableLayout = (LinearLayout)view.findViewById(R.id.buyMedicineLinearLayout2);
        ImageButton sendPrescriptionButton = (ImageButton)view.findViewById(R.id.buyMedicineImageButton1);
        ImageButton enterManuallyButton = (ImageButton)view.findViewById(R.id.buyMedicineImageButton2);
        ImageButton previousOrderButton = (ImageButton)view.findViewById(R.id.buyMedicineImageButton3);
        itemListFragment = (OrderItemListFragment)
                getChildFragmentManager().findFragmentByTag(OrderItemListFragment.TAG);

        if(itemListFragment == null)
            itemListFragment = OrderItemListFragment.newInstance();


        itemListFragment.setEditable(true);
        itemListFragment.setListViewPaddingTop(linearLayout.getLayoutParams().height +
                hidableLayout.getLayoutParams().height);


        android.support.v4.app.FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.buyMedicineListViewContainer,itemListFragment,OrderItemListFragment.TAG).commitAllowingStateLoss();

        /*TextView enterManuallyTextView = (TextView)view.findViewById(R.id.EnterManually);
        TextView sendPrescriptionTextView = (TextView)view.findViewById(R.id.SendPrescription);
        TextView previousOrderTextView = (TextView)view.findViewById(R.id.PreviousOrder);

        //Assign custom fonts
        Typeface typeFace=Typeface.createFromAsset(getActivity().getAssets(),"fonts/gothic.ttf");
        enterManuallyTextView.setTypeface(typeFace);
        sendPrescriptionTextView.setTypeface(typeFace);
        previousOrderTextView.setTypeface(typeFace);*/

        //Events on buttons
        sendPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.replaceFragment(R.layout.dialog_send_prescripiton,null);
            }
        });

        enterManuallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.replaceFragment(R.layout.dialog_search_medicine,null);
            }
        });


        //Here the current order list is being forwarded to the Previous Order Dialog
        //This is being done to prevent redundant entries from being entered from the previous order
        previousOrderButton.setOnClickListener(new View.OnClickListener() {
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

    public void showView(){
        hidableLayout.setVisibility(View.VISIBLE);
    }

    public void hideView(){
        hidableLayout.setVisibility(View.GONE);
    }

}
