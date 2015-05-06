package com.app.drugcorner32.dc_template.Fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

public class BuyMedicineFragment extends android.support.v4.app.Fragment {
    private OnFragmentChange callback1;
    private Callback callback2;
    private OrderDetails orderDetails = new OrderDetails();
    public static String TAG = "BuyMedicine";

    private OrderItemListFragment itemListFragment;

    private TextView noOfItemsView;
    private TextView costView;
    private ImageButton continueButton;
    private TextView itemAdded;

    public static BuyMedicineFragment newInstance() {
        BuyMedicineFragment fragment = new BuyMedicineFragment();
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
        TextView sendPrescriptionTextView = (TextView)view.findViewById(R.id.buyMedicineTextView1);
        TextView enterManuallyTextView = (TextView)view.findViewById(R.id.buyMedicineTextView2);
        TextView previousOrderTextView = (TextView)view.findViewById(R.id.buyMedicineTextView3);
        TextView helpView = (TextView)view.findViewById(R.id.buyMedicineTextView4);

        noOfItemsView = (TextView)view.findViewById(R.id.buyMedicineTextView5);
        costView = (TextView)view.findViewById(R.id.buyMedicineTextView6);
        continueButton = (ImageButton)view.findViewById(R.id.buyMedicineImageButton1);
        itemAdded = (TextView)view.findViewById(R.id.buyMedicineTextView7);

        Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/gothic.ttf");

        sendPrescriptionTextView.setTypeface(typeface);
        enterManuallyTextView.setTypeface(typeface);
        previousOrderTextView.setTypeface(typeface);
        helpView.setTypeface(typeface);
        itemAdded.setTypeface(typeface);
        costView.setTypeface(typeface);
        noOfItemsView.setTypeface(typeface);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.buyMedicineLinearLayout1);

        itemListFragment = (OrderItemListFragment)
                getChildFragmentManager().findFragmentByTag(OrderItemListFragment.TAG);

        if(itemListFragment == null)
            itemListFragment = OrderItemListFragment.newInstance();

        itemListFragment.setOrderDetails(orderDetails);
        itemListFragment.setEditable(true);
        itemListFragment.setRemovable(true);

        android.support.v4.app.FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.buyMedicineListViewContainer,itemListFragment,OrderItemListFragment.TAG).commitAllowingStateLoss();
        //Events on buttons

        sendPrescriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback1.replaceFragment(R.layout.dialog_send_prescripiton, null);
            }
        });

        enterManuallyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback1.replaceFragment(R.layout.dialog_search_medicine, null);
            }
        });

        //Here the current order list is being forwarded to the Previous Order Dialog
        previousOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback1.replaceFragment(R.layout.dialog_previous_order, itemListFragment.getItemDetailses());
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback2.startAddressActivity(orderDetails);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback1 = (OnFragmentChange) activity;
            callback2 = (Callback)activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback1 = null;
        callback2 = null;
    }

    public void addNewPrescription(Uri imagePath){
        itemListFragment.addOrderItem(new OrderItemDetails(new PrescriptionDetails(imagePath)));
    }

    public void addNewMedicine(MedicineDetails medicineDetails){
        itemListFragment.addOrderItem(new OrderItemDetails(medicineDetails,true));
    }

    public void repeatOrder(OrderDetails details){
        if(itemListFragment!=null)
            itemListFragment.addPreviousOrder(details);
    }


    public void updateCartAdapter(){
        if(itemListFragment!=null)
            itemListFragment.updateCartAdapter();
    }

    public void updateBottomMenu(){
        //Updating the bottom menu
        if(noOfItemsView != null) {
            noOfItemsView.setText(itemListFragment.getItemDetailses().size() + "");

            float cost = 0;
            for (OrderItemDetails itemDetails : itemListFragment.getItemDetailses())
                cost += itemDetails.getCost();
            costView.setText(cost + "/-");
        }
    }

    public interface Callback{
        void startAddressActivity(OrderDetails details);
    }
}
