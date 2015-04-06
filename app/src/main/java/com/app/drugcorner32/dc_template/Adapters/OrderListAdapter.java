package com.app.drugcorner32.dc_template.Adapters;

import android.content.Context;
import android.graphics.Typeface;
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
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 04-03-2015.
 */
public class OrderListAdapter extends ArrayAdapter<OrderDetails> {

    private Callback callback;
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
        callback = (Callback)context;
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
            TextView orderStatus = (TextView)view.findViewById(R.id.orderStatusTextView);
            TextView orderAddress0 = (TextView)view.findViewById(R.id.orderAddressTextView);
            TextView orderAddress1 = (TextView)view.findViewById(R.id.orderAddressTextView1);
            TextView orderAddress3 = (TextView)view.findViewById(R.id.orderAddressTextView13);
            TextView orderAddress2 = (TextView)view.findViewById(R.id.orderAddressTextView12);
            TextView orderAddress4 = (TextView)view.findViewById(R.id.orderAddressTextView14);
            TextView orderAddress5 = (TextView)view.findViewById(R.id.orderAddressTextView15);
            TextView orderAddress6 = (TextView)view.findViewById(R.id.orderAddressTextView16);
            TextView orderDate = (TextView)view.findViewById(R.id.orderDateTextView);
            TextView orderAmount = (TextView)view.findViewById(R.id.orderAmountTextView);
            TextView orderNo = (TextView)view.findViewById(R.id.orderNoTextView);
            TextView orderRepeat  = (TextView)view.findViewById(R.id.orderRepeat);
            TextView orderSelect  = (TextView)view.findViewById(R.id.orderSelect);


            Typeface typeFace=Typeface.createFromAsset(orderStatus.getContext().getAssets(),"fonts/gothic.ttf");
            orderStatus.setTypeface(typeFace);
            orderAddress0.setTypeface(typeFace);
            orderAddress1.setTypeface(typeFace);
            orderAddress2.setTypeface(typeFace);
            orderAddress3.setTypeface(typeFace);
            orderAddress4.setTypeface(typeFace);
            orderAddress5.setTypeface(typeFace);
            orderAddress6.setTypeface(typeFace);
            orderDate.setTypeface(typeFace);
            orderAmount.setTypeface(typeFace);
            orderNo.setTypeface(typeFace);
            orderRepeat.setTypeface(typeFace);
            orderSelect.setTypeface(typeFace);


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
            holder.checkBox.setVisibility(View.GONE);
        }
        else{
            holder.checkBox.setVisibility(View.GONE);
        }

        OrderDetails details = getItem(position);
        holder.orderNo.setText("drugCorner"+details.getOrderNo() + "");

        holder.checkBox.setChecked(details.getSelection());
        //displays two decimal digits of the amount
        holder.orderAmount.setText(details.getOrderAmount() + " /-");

        //holder.orderDate.setText(details.getOrderDate() + "");
        holder.orderAddress.setText(details.getOrderDeliveryAddress());
        holder.orderStatus.setText("" + details.getOrderStatus().toString());
        return view;
    }

    public void setSelectable(boolean value){
        isSelctable = value;
    }

    public static interface Callback{
        public void deliverOrderToDialog(OrderDetails details);
    }
}
