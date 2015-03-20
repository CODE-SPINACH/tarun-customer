package com.app.drugcorner32.dc_template.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 04-03-2015.
 */
public class OrderListAdapter extends ArrayAdapter<OrderDetails> {

    private OnFragmentInteractionListener callback;
    public static class ViewHolder{
        //Hidden views made visible at the time of selection from previous order
        public CheckBox checkBox;
        public ImageView orderDetailImage;

        public TextView orderNo;
        public TextView orderDate;
        public TextView orderAddress;

        public TextView orderAmount;

        public TextView orderStatus;
    }

    //Sets whether to display the hidden views . i.e. they will be displayed in selection from previous order
    private boolean isSelctable = false;

    private List<OrderDetails> orderList = new ArrayList<OrderDetails>();

    public OrderListAdapter(Context context, int cardViewResourceId){
        super(context,cardViewResourceId);
        callback = (OnFragmentInteractionListener)context;
    }

    @Override
    public void add(OrderDetails object) {
        orderList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.orderList.size();
    }

    @Override
    public OrderDetails getItem(int index) {
        return this.orderList.get(index);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;

        ViewHolder holder = new ViewHolder();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cards_order, parent, false);

            holder.checkBox = (CheckBox) view.findViewById(R.id.orderCheckBox);

            holder.orderNo = (TextView) view.findViewById(R.id.orderNoTextView);
            holder.orderAmount = (TextView) view.findViewById(R.id.orderAmountTextView);
            holder.orderDate = (TextView) view.findViewById(R.id.orderDateTextView);
            holder.orderAddress = (TextView) view.findViewById(R.id.orderAddressTextView);

            holder.orderStatus = (TextView) view.findViewById(R.id.orderStatusTextView);

            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    OrderDetails details = orderList.get(position);

                    details.setSelection(isChecked);
                    for(OrderItemDetails item : details.getOrderItemsList()){
                        if(!item.getDisabled()) {
                            if (item.getOrderType() == OrderItemDetails.TypesOfOrder.TRANSLATED_PRESCRIPTION)
                                for (MedicineDetails details1 : item.getPrescriptionDetails().getMedicineList())
                                    details1.setSelection(isChecked);

                            item.setSelected(isChecked);
                        }
                    }
                    callback.deliverOrderToDialog(details);
                }
            });

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder)view.getTag();
        }

        if(isSelctable){
            holder.checkBox.setVisibility(View.VISIBLE);
        }
        else{
            holder.checkBox.setVisibility(View.GONE);
        }

        OrderDetails details = getItem(position);
        holder.orderNo.setText("Order No : "+details.getOrderNo() + "");

        holder.checkBox.setChecked(details.getSelection());
        //displays two decimal digits of the amount
        holder.orderAmount.setText(details.getOrderAmount() + " /-");

        //holder.orderDate.setText(details.getOrderDate() + "");
        holder.orderAddress.setText(details.getOrderDeliveryAddress());
        holder.orderStatus.setText("Status : " + details.getOrderStatus().toString());
        return view;
    }

    public void setSelectable(boolean value){
        isSelctable = value;
    }

}