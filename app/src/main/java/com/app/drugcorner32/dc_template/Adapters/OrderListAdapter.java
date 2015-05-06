package com.app.drugcorner32.dc_template.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 04-03-2015.
 */
public class OrderListAdapter extends ArrayAdapter<OrderDetails> {

    private SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMMM-yyyy");

    private OnFragmentChange callback1;
    private Callback callback2;
    public static class ViewHolder{
        public TextView orderRecipientNameView;
        public TextView orderDateView;
        public TextView orderMedicineNamesView;
        public TextView orderAmountView;
        public TextView orderStatusView;
        public TextView pickMedicineView;
        public TextView repeatView;
    }

    //Sets whether to display the hidden views . i.e. they will be displayed in selection from previous order
    private boolean isSelctable = false;

    private List<OrderDetails> orderList = new ArrayList<OrderDetails>();

    public OrderListAdapter(Context context, int cardViewResourceId){
        super(context,cardViewResourceId);
        callback1 = (OnFragmentChange)context;
        callback2 = (Callback)context;
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

            holder.orderRecipientNameView= (TextView) view.findViewById(R.id.orderCardTextView1);
            holder.orderAmountView = (TextView) view.findViewById(R.id.orderCardTextView2);
            holder.orderDateView= (TextView) view.findViewById(R.id.orderCardTextView3);
            holder.orderStatusView= (TextView) view.findViewById(R.id.orderCardTextView4);
            holder.orderMedicineNamesView = (TextView) view.findViewById(R.id.orderCardTextView5);
            holder.pickMedicineView= (TextView) view.findViewById(R.id.orderCardTextView6);
            holder.repeatView= (TextView) view.findViewById(R.id.orderCardTextView7);

            Typeface typeFace=Typeface.createFromAsset(getContext().getAssets(),"fonts/gothic.ttf");
            holder.repeatView.setTypeface(typeFace);
            holder.pickMedicineView.setTypeface(typeFace);
            holder.orderStatusView.setTypeface(typeFace);
            holder.orderMedicineNamesView.setTypeface(typeFace);
            holder.orderAmountView.setTypeface(typeFace);
            holder.orderDateView.setTypeface(typeFace);
            holder.orderRecipientNameView.setTypeface(typeFace);

            view.setTag(holder);
        }
        else
            holder = (ViewHolder)view.getTag();

        final OrderDetails details = getItem(position);
        holder.orderRecipientNameView.setText(details.getOrderNo()+"");
        holder.orderAmountView.setText(details.getOrderAmount() + " /-");
        holder.orderStatusView.setText("" + details.getOrderStatus().toString());
        holder.orderDateView.setText(dateFormat.format(details.getOrderDate()));
        String appendedMedicineName = "";
        for(OrderItemDetails itemDetails : details.getOrderItemsList()){
            if(itemDetails.getOrderType() == OrderItemDetails.TypesOfOrder.PRESCRIPTION)
                if(itemDetails.getPrescriptionDetails().getPrescriptionType() ==
                        PrescriptionDetails.TypesOfPrescription.TRANSLATED_PRESCRIPTION)
                    for(MedicineDetails medicineDetails: itemDetails.getPrescriptionDetails().getMedicineList())
                        appendedMedicineName += medicineDetails.getMedicineName() + ",";
            else
                appendedMedicineName += itemDetails.getMedicineDetails().getMedicineName() + ",";
        }
        if(appendedMedicineName.length() > 0)
            appendedMedicineName = appendedMedicineName.substring(0,appendedMedicineName.length() - 1);

        holder.orderMedicineNamesView.setText(appendedMedicineName);

        holder.pickMedicineView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback1.replaceFragment(R.id.orderCardTextView6, getItem(position));
            }
        });

        holder.repeatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback2.repeatOrder(getItem(position));
            }
        });

        return view;
    }

    public void setSelectable(boolean value){
        isSelctable = value;
    }

    public static interface Callback{
        public void repeatOrder(OrderDetails details);
    }
}
