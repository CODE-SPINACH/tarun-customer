package com.app.drugcorner32.dc_template.Dialogs;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Fragments.OrderItemListFragment;
import com.app.drugcorner32.dc_template.Fragments.PreviousOrderListFragment;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 14-03-2015.
 *
 * Selection process works as follows:
 * If an order is opened it is added to the selection List for pruning
 * If an order is checked it is added
 * All the other orders are left as it is
 *
 * Also if an order is opened but nothing is selected from it then it is removed
 */
public class PreviousOrderDialog extends DialogFragment {

    public static String TAG = "PreviousOrder";

    private OnFragmentChange callBack;

    private List<OrderItemDetails> cartOrderItemsList;

    private OrderDetails selectedOrderDetails;

    private List<OrderDetails> selectedOrderDetailsList = new ArrayList<>();

    private TextView toolbarTitleView;
    private ImageButton toolbarBackButton;
    private TextView noOfItemsView;
    private TextView costView;
    private TextView backToCartView;
    private ImageButton closeButton;

    public PreviousOrderDialog() {
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
        callBack = (OnFragmentChange) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_previous_order, container, false);

        toolbarBackButton = (ImageButton)view.findViewById(R.id.previousOrderDialogImageButton1);
        toolbarTitleView = (TextView) view.findViewById(R.id.previousOrderDialogTextView1);
        closeButton = (ImageButton)view.findViewById(R.id.previousOrderDialogImageButton2);
        noOfItemsView = (TextView) view.findViewById(R.id.previousOrderDialogTextView2);
        costView = (TextView) view.findViewById(R.id.previousOrderDialogTextView3);
        backToCartView = (TextView) view.findViewById(R.id.previousOrderDialogTextView4);

        toolbarTitleView.setText("Previous Orders");
        noOfItemsView.setText(cartOrderItemsList.size() + "");

        float cost = 0;
        for(OrderItemDetails details : cartOrderItemsList)
            cost += details.getCost();
        costView.setText(cost + "/-");

        PreviousOrderListFragment previousOrderListFragment = (PreviousOrderListFragment)
                getChildFragmentManager().findFragmentByTag(PreviousOrderListFragment.TAG);

        if(previousOrderListFragment == null) {
            previousOrderListFragment = PreviousOrderListFragment.newInstance();
        }

        previousOrderListFragment.setSelectable(true);

        getChildFragmentManager().beginTransaction().
                replace(R.id.previousOrderDialogFrameLayout1, previousOrderListFragment, PreviousOrderListFragment.TAG).
                addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();

        toolbarBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarBackButton.setVisibility(View.GONE);
                getChildFragmentManager().popBackStack();
                toolbarTitleView.setText("Previous Orders");
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        backToCartView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    public void changeFragment(OrderItemListFragment fragment) {
        if(!selectedOrderDetailsList.contains(selectedOrderDetails))
            selectedOrderDetailsList.add(selectedOrderDetails);
        if(getView() !=null) {
            toolbarBackButton.setVisibility(View.VISIBLE);

            getChildFragmentManager().beginTransaction().replace(R.id.previousOrderDialogFrameLayout1, fragment,
                    OrderItemListFragment.TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).
                    commitAllowingStateLoss();
        }
        toolbarTitleView.setText("Order Items");
    }

    public void setCartOrderItemsList(List<OrderItemDetails> orderItemsList){
        cartOrderItemsList = orderItemsList;
    }

    public void setSelectedOrderDetails(OrderDetails details){
        selectedOrderDetails = details;
    }

    public void addOrderToSelectList(OrderDetails details){
        if(!selectedOrderDetailsList.contains(details))
            selectedOrderDetailsList.add(details);
    }

    //if details is null , then it means that the quantity of an existing item has been manipulated
    //it would simply reflect changes in the bottom menu

    public void updateCart(OrderItemDetails details){
        if(details != null) {
            if (cartOrderItemsList.contains(details)) {
                if (details.getOrderType() == OrderItemDetails.TypesOfOrder.OTC) {
                    cartOrderItemsList.remove(details);
                } else {
                    boolean delete = true;
                    for (MedicineDetails medicineDetails : details.getPrescriptionDetails().getMedicineList())
                        if (medicineDetails.getQuantity() != 0)
                            delete = false;
                    if (delete)
                        cartOrderItemsList.remove(details);
                }
            }
            else
                cartOrderItemsList.add(details);
        }

        //Updating the bottom menu
        if(noOfItemsView != null) {
            noOfItemsView.setText(cartOrderItemsList.size() + "");

            float cost = 0;
            for (OrderItemDetails itemDetails : cartOrderItemsList)
                cost += itemDetails.getCost();
            costView.setText(cost + "/-");
        }

    }

}