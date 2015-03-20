package com.app.drugcorner32.dc_template.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Fragments.OrderItemListFragment;
import com.app.drugcorner32.dc_template.Fragments.PreviousOrderListFragment;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentInteractionListener;
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

    private OnFragmentInteractionListener callBack;

    private List<OrderItemDetails> cartOrderItemsList;

    private OrderDetails selectedOrderDetails;

    private List<OrderDetails> selectedOrderDetailsList = new ArrayList<>();

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
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);

        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callBack = (OnFragmentInteractionListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_previous_order, container, false);
        PreviousOrderListFragment previousOrderListFragment = (PreviousOrderListFragment)
                getChildFragmentManager().findFragmentByTag(PreviousOrderListFragment.TAG);

        if(previousOrderListFragment == null) {
            previousOrderListFragment = PreviousOrderListFragment.newInstance();
        }

        previousOrderListFragment.setSelectable(true);

        getChildFragmentManager().beginTransaction().
                replace(R.id.previousOrderDialogFrameLayout1, previousOrderListFragment, PreviousOrderListFragment.TAG).
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commitAllowingStateLoss();

        final ImageButton backButton = (ImageButton)view.findViewById(R.id.previousOrderDialogImageButton1);
        final TextView t = (TextView) view.findViewById(R.id.previousOrderDialogTextView1);
        Button addButton = (Button)view.findViewById(R.id.previousOrderDialogButton1);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.replaceFragment(R.id.previousOrderDialogButton1,selectedOrderDetailsList);
                dismiss();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton.setVisibility(View.GONE);
                t.setVisibility(View.VISIBLE);
                OrderItemListFragment itemListFragment = (OrderItemListFragment)
                        getChildFragmentManager().findFragmentByTag(OrderItemListFragment.TAG);

                PreviousOrderListFragment orderListFragment = (PreviousOrderListFragment)
                        getChildFragmentManager().findFragmentByTag(PreviousOrderListFragment.TAG);

                //This ensures that an order that has no item in it selected, is removed
                //result = 2 means all select
                //         1 means some selected
                //         0 means none selected

                int result = itemListFragment.countAndTell();
                if(result<2) {
                    orderListFragment.selectCurrentOrder(false);
                    if(result == 0) {
                        selectedOrderDetailsList.remove(selectedOrderDetails);
                    }
                }
                else
                    orderListFragment.selectCurrentOrder(true);

                getChildFragmentManager().popBackStack();
            }
        });

        return view;
    }

    public void changeFragment(OrderItemListFragment fragment) {
        if(!selectedOrderDetailsList.contains(selectedOrderDetails))
            selectedOrderDetailsList.add(selectedOrderDetails);

        //TODO: not working as of
        for(OrderItemDetails details : cartOrderItemsList) {
            if(selectedOrderDetails.getOrderItemsList().contains(details))
                details.setDisabled(true);
            }

        TextView t = (TextView) getView().findViewById(R.id.previousOrderDialogTextView1);
        ImageButton imageButton = (ImageButton) getView().findViewById((R.id.previousOrderDialogImageButton1));
        t.setVisibility(View.GONE);
        imageButton.setVisibility(View.VISIBLE);

        getChildFragmentManager().beginTransaction().replace(R.id.previousOrderDialogFrameLayout1, fragment,
                OrderItemListFragment.TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                addToBackStack(null).commitAllowingStateLoss();
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

}