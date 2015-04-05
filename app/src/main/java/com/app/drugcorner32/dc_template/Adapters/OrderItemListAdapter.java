package com.app.drugcorner32.dc_template.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 10-03-2015.
 */
public class OrderItemListAdapter extends BaseAdapter {

    Callback callback;

    /*Sets whether the item can be edited or not. e.g. in
    previous orders the items won't be edited. */
    private boolean isEditable = false;

    /*Sets so as to make the item selectable.
    The item would be selectable only in the previous order and the
    edit the current order screen
     */
    private boolean isSelectable = false;

    private boolean childChnage = false;

    private List<OrderItemDetails> itemDetailsList = new ArrayList<>();

    private int selectionCount = 0;

    private Context context;

    private Integer[] spinnerArray = new Integer[100];

    public OrderItemListAdapter(Context context){
        this.context = context;
        callback = (Callback)context;

        for(int i =0;i<100;i++) {
            spinnerArray[i] = i + 1;
        }
    }

    public void add(OrderItemDetails item){
        itemDetailsList.add(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public OrderItemDetails getItem(int position) {
        return itemDetailsList.get(position);
    }

    @Override
    public int getCount() {
        return itemDetailsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (itemDetailsList.get(position).getOrderType())
        {
            case TRANSLATED_PRESCRIPTION:
                return 0;
            case UNTRANSLATED_PRESCRIPTION:
                return 1;
            case OTC:
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        class ViewHolder0{
            public LinearLayout maximizedLayout;
            public LinearLayout minimizedLayout;
            public LinearLayout editLayout;
            public ArrayList<LinearLayout> linearLayouts;
            public ImageButton crossButton;
            public CheckBox checkBox;
            public TextView costView;
            public TextView numberOfDrugsView;

            public int count = 0;
        }

        class ViewHolder1{

            public LinearLayout minimizedLayout;
            public ImageView prescriptionImage;
            public ImageButton crossButton;
            public TextView prescriptionCountView;
        }

        class ViewHolder2{
            public LinearLayout minimizedLayout;
            public LinearLayout maximizedLayout;
            public CheckBox checkBox;
            public TextView nameOfMedicineTextView;
            public TextView costOfMedicineTextView;
            public ImageView typeOfMedicineImageView;
            public ImageButton crossButton;
            public RadioGroup radioGroup;
            public RadioButton tabletRadioButton;
            public RadioButton stripsRadioButton;
            public RadioButton bottlesRadioButton;
            public Spinner quantityPicker;
        }

        //sets whether to show the children or not
        final boolean isExpanded = getItem(position).getExpanded();

        int viewType = getItemViewType(position);
        switch (viewType){
            case 0:
                final ViewHolder0 holder0;
                final int childCount = getItem(position).getPrescriptionDetails().getMedicineList().size();
                if(view == null) {
                    holder0 = new ViewHolder0();
                    view = inflater.inflate(R.layout.cards_translated_prescription, parent, false);

                    //The vertical layout where the children are to be added i.e. the medicines in the prescription
                    holder0.maximizedLayout = (LinearLayout) view.findViewById(R.id.translatedPrescriptionLinearLayout4);

                    //The horizontal layout on whose touch event the children are to be shown
                    holder0.minimizedLayout = (LinearLayout) view.findViewById(R.id.translatedPrescriptionLinearLayout2);

                    //The array contains all the child views which are being added dynamically
                    holder0.linearLayouts = new ArrayList<>(childCount);

                   // holder0.editLayout = (LinearLayout)view.findViewById(R.id.translatedPrescriptionLinearLayout3);
                   // holder0.crossButton = (ImageButton)view.findViewById(R.id.translatedPrescriptionImageButton2);
                    holder0.checkBox = (CheckBox)view.findViewById(R.id.translatedPrescriptionCheckBox1);

                    holder0.costView = (TextView)view.findViewById(R.id.translatedPrescriptionTextView2);

                    holder0.numberOfDrugsView = (TextView)view.findViewById(R.id.translatedPrescriptionTextView1);

                    //The children are being set i.e. the medicines in the prescription
                    for(int i = 0;i< childCount;i++) {
                        holder0.linearLayouts.add((LinearLayout) inflater.
                                inflate(R.layout.cards_child_prescription, null, false));
                        LinearLayout layout = holder0.linearLayouts.get(i);

                        if(getItem(position).getPrescriptionDetails().getMedicineList().get(i).getDisabled()) {
                            layout.findViewById(R.id.prescriptionChildCheckBox1).setEnabled(false);
                        }

                        /*The items are added at index 1 to add them between
                        the tick button and the main layout
                          */

                        holder0.maximizedLayout.addView(layout, i);
                    }
                    if(getItem(position).getDisabled())
                        holder0.checkBox.setEnabled(false);

                    view.setTag(holder0);
               }
                else
                    holder0 = (ViewHolder0)view.getTag();

                holder0.checkBox.setChecked(getItem(position).getSelected());
                holder0.count = 0;

                holder0.numberOfDrugsView.setText(""+childCount + " DRUGS");
                holder0.costView.setText(getItem(position).getPrescriptionDetails().getCost() + "/-");

                if(childCount != holder0.linearLayouts.size()) {
                    holder0.maximizedLayout.removeAllViews();
                    holder0.linearLayouts.clear();
                    for (int i = 0; i < childCount; i++) {
                        holder0.linearLayouts.add((LinearLayout) inflater.
                                inflate(R.layout.cards_child_prescription, null, false));
                        holder0.maximizedLayout.addView(holder0.linearLayouts.get(i), i);
                    }
                }


                for(int i = 0;i<childCount;i++) {
                    final MedicineDetails medicineDetails = getItem(position).getPrescriptionDetails().getMedicineList().get(i);
                    final LinearLayout layout = holder0.linearLayouts.get(i);

                    TextView nameOfMedicineView = (TextView) layout.findViewById(R.id.prescriptionChildTextView1);
                    TextView costView = (TextView) layout.findViewById(R.id.prescriptionChildTextView2);
                    Spinner quantityPicker = (Spinner) layout.findViewById(R.id.prescriptionChildSpinner1);
                   // ImageButton crossButton = (ImageButton) layout.findViewById(R.id.prescriptionChildImageButton1);
                    CheckBox checkBox = (CheckBox) layout.findViewById(R.id.prescriptionChildCheckBox1);

                    ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,spinnerArray);
                    quantityPicker.setAdapter(spinnerAdapter);

                    quantityPicker.setSelection(medicineDetails.getQuantity());
                    nameOfMedicineView.setText(medicineDetails.getMedicineName());
                    costView.setText(medicineDetails.getCost() * medicineDetails.getDays() * medicineDetails.getQuantityPerDay() + "/-");
                    checkBox.setChecked(medicineDetails.getSelection());

                    if(isSelectable) {
                        quantityPicker.setClickable(false);
                        quantityPicker.setEnabled(false);
                        checkBox.setVisibility(View.VISIBLE);
                        if (medicineDetails.getSelection())
                            holder0.count++;
                    }

                   /* if(isEditable){
                        crossButton.setVisibility(View.VISIBLE);
                    }*/

                    //child events
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            medicineDetails.setSelection(isChecked);
                            if (isChecked)
                                holder0.count++;
                            else
                                holder0.count--;

                            if (holder0.count == 0) {
                                getItem(position).setSelected(false);
                                holder0.checkBox.setChecked(false);
                            } else if (!getItem(position).getSelected() && holder0.count == 1) {
                                childChnage = true;
                                getItem(position).setSelected(true);
                                holder0.checkBox.setChecked(true);
                            }
                        }
                    });


                   /* crossButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holder0.maximizedLayout.removeView(layout);
                            holder0.linearLayouts.remove(layout);
                            getItem(position).getPrescriptionDetails().getMedicineList().remove(medicineDetails);
                            if(holder0.linearLayouts.size() == 0)
                                itemDetailsList.remove(position);
                            notifyDataSetChanged();
                        }
                    });*/

                    quantityPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            medicineDetails.setQuantity(position + 1);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }



                if(isEditable){
                   // holder0.editLayout.setVisibility(View.VISIBLE);
                  //  holder0.crossButton.setVisibility(View.VISIBLE);
                }

                if(isSelectable){
                    holder0.checkBox.setVisibility(View.VISIBLE);
                }

                if(isExpanded)
                    holder0.maximizedLayout.setVisibility(View.VISIBLE);

                //Events :

                holder0.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        getItem(position).setSelected(isChecked);
                        if(!childChnage){
                                for (int i = 0; i < childCount; i++) {
                                    itemDetailsList.get(position).getPrescriptionDetails().getMedicineList().get(i).setSelection(isChecked);
                                    CheckBox c = ((CheckBox) holder0.linearLayouts.get(i).findViewById(R.id.prescriptionChildCheckBox1));
                                    c.setChecked(isChecked);
                                }
                            }
                        childChnage = false;
                    }
                });


               /* holder0.crossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailsList.remove(position);
                        notifyDataSetChanged();
                    }
                });*/


                holder0.minimizedLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(holder0.maximizedLayout.getVisibility() == View.VISIBLE) {
                            holder0.maximizedLayout.setVisibility(View.GONE);
                            getItem(position).setExpanded(false);
                        }
                        else {
                            holder0.maximizedLayout.setVisibility(View.VISIBLE);
                            getItem(position).setExpanded(true);
                        }

                    }
                });

                break;
            case 1:
                final PrescriptionDetails prescriptionDetails = getItem(position).getPrescriptionDetails();
                final ViewHolder1 holder1;

                int count = 0 ;

                if(view == null) {
                    view = inflater.inflate(R.layout.cards_prescription, parent, false);

                    holder1 = new ViewHolder1();

                    holder1.minimizedLayout = (LinearLayout) view.findViewById(R.id.prescriptionCardLinearLayout1);
                    holder1.prescriptionImage = (ImageView) view.findViewById(R.id.prescriptionCardImageView1);
                    //holder1.crossButton = (ImageButton) view.findViewById(R.id.prescriptionCardImageButton1);
                    holder1.prescriptionCountView = (TextView) view.findViewById(R.id.prescriptionCardTextView1);
                    holder1.prescriptionImage.setImageBitmap(prescriptionDetails.getThumbnail());

                    for(OrderItemDetails details : itemDetailsList){
                        if(details.getOrderType() == OrderItemDetails.TypesOfOrder.UNTRANSLATED_PRESCRIPTION) {
                            if(details.equals(getItem(position)))
                                break;
                            count++;
                        }
                    }

                    holder1.prescriptionCountView.setText("PRESCRIPTION " + (count+1));

                    view.setTag(holder1);
                }
                else
                     holder1 = (ViewHolder1)view.getTag();


                //events :

               /* holder1.crossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailsList.remove(position);
                        notifyDataSetChanged();
                    }
                });*/

                //expand the view for edit on clicking the minimized view
              /*  holder1.minimizedLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.replaceFragment(R.id.prescriptionCardImageButton1,prescriptionDetails.getImageUri());
                    }
                });*/

                break;

            case 2:
                //TODO set check on the max number of medicines quantity that are allowed
                final MedicineDetails medicineDetails = getItem(position).getMedicineDetails();
                final ViewHolder2 holder2;
                if(view == null) {
                    view = inflater.inflate(R.layout.cards_medicine, parent, false);
                    holder2 = new ViewHolder2();

                    holder2.minimizedLayout = (LinearLayout) view.findViewById(R.id.medicineCardLinearLayout2);
                    holder2.maximizedLayout = (LinearLayout) view.findViewById(R.id.medicineCardLinearLayout3);

                    holder2.checkBox = (CheckBox) view.findViewById(R.id.medicineCardCheckBox1);
                    holder2.nameOfMedicineTextView = (TextView) view.findViewById(R.id.medicineCardTextView1);
                    holder2.costOfMedicineTextView = (TextView) view.findViewById(R.id.medicineCardTextView3);
                    holder2.radioGroup = (RadioGroup)view.findViewById(R.id.medicineCardRadioGroup1);
                    holder2.typeOfMedicineImageView = (ImageView) view.findViewById(R.id.medicineCardImageView1);
                   // holder2.crossButton = (ImageButton) view.findViewById(R.id.medicineCardImageButton1);
                    holder2.tabletRadioButton = (RadioButton) view.findViewById(R.id.medicineCardRadioButton1);
                    holder2.stripsRadioButton = (RadioButton) view.findViewById(R.id.medicineCardRadioButton2);
                    holder2.bottlesRadioButton = (RadioButton) view.findViewById(R.id.medicineCardRadioButton3);
                    holder2.quantityPicker = (Spinner) view.findViewById(R.id.medicineCardSpinner1);

                    ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(context,android.R.layout.simple_spinner_dropdown_item,spinnerArray);

                    holder2.quantityPicker.setAdapter(spinnerAdapter);

                    if(getItem(position).getDisabled())
                        holder2.checkBox.setEnabled(false);

                    view.setTag(holder2);
                }
                else
                    holder2 = (ViewHolder2)view.getTag();


                holder2.checkBox.setChecked(getItem(position).getSelected());
                holder2.nameOfMedicineTextView.setText(medicineDetails.getMedicineName());

                holder2.quantityPicker.setSelection(medicineDetails.getQuantity() - 1);
                holder2.quantityPicker.setClickable(false);
                holder2.quantityPicker.setEnabled(false);

                if(isExpanded) {
                    holder2.maximizedLayout.setVisibility(View.VISIBLE);
                    holder2.quantityPicker.setClickable(true);
                    holder2.quantityPicker.setEnabled(true);
                }

                if(isSelectable)
                    holder2.checkBox.setVisibility(View.VISIBLE);

              /*  if(!isEditable)
                    holder2.crossButton.setVisibility(View.GONE);*/

                //TODO : change the type of medicine image view here
                //using typeofMedicineImageView

                if(medicineDetails.getMedicineType() == MedicineDetails.MedicineTypes.Bottles){
                    holder2.bottlesRadioButton.setVisibility(View.VISIBLE);
                    holder2.bottlesRadioButton.setChecked(true);
                }
                else{
                    holder2.tabletRadioButton.setVisibility(View.VISIBLE);
                    holder2.stripsRadioButton.setVisibility(View.VISIBLE);
                    if(medicineDetails.getMedicineType() == MedicineDetails.MedicineTypes.Strips)
                        holder2.stripsRadioButton.setChecked(true);
                    else
                        holder2.tabletRadioButton.setChecked(true);
                }

                holder2.quantityPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int newVal = position + 1;
                        medicineDetails.setQuantity(newVal);

                        holder2.costOfMedicineTextView.setText(""+(newVal * 20.0) + "/-");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                holder2.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        getItem(position).setSelected(isChecked);
                    }
                });

               /* holder2.crossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailsList.remove(position);
                        notifyDataSetChanged();
                    }
                });*/

                holder2.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.medicineCardRadioButton1:
                                medicineDetails.setMedicineType(MedicineDetails.MedicineTypes.Tablets);
                                break;
                            case R.id.medicineCardRadioButton2:
                                medicineDetails.setMedicineType(MedicineDetails.MedicineTypes.Strips);
                                break;
                        }
                    }
                });

                if(isEditable)
                    holder2.minimizedLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(holder2.maximizedLayout.getVisibility() == View.VISIBLE) {
                                itemDetailsList.get(position).setExpanded(false);
                                holder2.maximizedLayout.setVisibility(View.GONE);
                                notifyDataSetChanged();
                            }
                            else{
                                itemDetailsList.get(position).setExpanded(true);
                                holder2.maximizedLayout.setVisibility(View.VISIBLE);
                                notifyDataSetChanged();
                            }
                        }
                    });

                break;

        }
        return view;
    }

    public void setEditable(boolean val){
        isEditable = val;
    }
    public void setSelectable(boolean val){
        isSelectable = val;
    }

    public void setItemDetailsList(List<OrderItemDetails> list){
        itemDetailsList = list;
    }

    public List<OrderItemDetails> getItemDetailsList(){ return itemDetailsList; }

    public static interface Callback{
        public void replaceFragment(int id,Object o);
    }
}
