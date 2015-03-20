package com.app.drugcorner32.dc_template.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentInteractionListener;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 10-03-2015.
 */
public class OrderItemListAdapter extends BaseAdapter {

    OnFragmentInteractionListener callback;

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

    public OrderItemListAdapter(Context context){
        this.context = context;
        callback = (OnFragmentInteractionListener)context;
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
            public LinearLayout parentLayout;
            public LinearLayout minimizedLayout;
            public LinearLayout editLayout;
            public ArrayList<LinearLayout> linearLayouts;
            public ImageButton checkImageButton;
            public ImageButton crossButton;
            public CheckBox checkBox;
            public TextView costView;

            public TextView incrementDaysView;
            public TextView decrementDaysView;

            public int count = 0;
        }

        class ViewHolder1{
            public LinearLayout minimizedLayout;
            public LinearLayout maximizedLayout;
            public ImageView prescriptionImage;
            public ImageButton crossButton;
            public ImageButton checkButton;
            public  EditText notesEditText;
            public TextView daysPrescriptionTextView;
            public NumberPicker daysPicker;
        }

        class ViewHolder2{
            public LinearLayout minimizedLayout;
            public LinearLayout maximizedLayout;
            public CheckBox checkBox;
            public TextView nameOfMedicineTextView;
            public TextView quantityOfMedicineTextView;
            public TextView costOfMedicineTextView;
            public ImageView typeOfMedicineImageView;
            public ImageButton crossButton;
            public RadioGroup radioGroup;
            public RadioButton tabletRadioButton;
            public RadioButton stripsRadioButton;
            public RadioButton bottlesRadioButton;
            public NumberPicker quantityPicker;
            public ImageButton checkButton;
        }

        //sets whether to show the children or not
        final boolean isExpanded = getItem(position).getExpanded();

        int viewType = getItemViewType(position);
        switch (viewType){
            case 0:
                //TODO: ALSO WHEN THE CHILDREN ARE ALL REMOVED THE PRESCRIPTION SHOULD BE REMOVED TOO
                final int childCount = getItem(position).getPrescriptionDetails().getMedicineList().size();
                final ViewHolder0 holder0;
                if(view == null) {
                    holder0 = new ViewHolder0();

                    view = inflater.inflate(R.layout.cards_translated_prescription, parent, false);

                    //The vertical layout where the children are to be added
                    holder0.parentLayout = (LinearLayout) view.findViewById(R.id.translatedPrescriptionLinearLayout4);

                    //The horizontal layout on whose touch event the children are to be shown
                    holder0.minimizedLayout = (LinearLayout) view.findViewById(R.id.translatedPrescriptionLinearLayout2);

                    holder0.checkImageButton = (ImageButton) view.findViewById(R.id.translatedPrescriptionImageButton3);

                    //The array contains all the child views which are being added dynamically
                    holder0.linearLayouts = new ArrayList<>();

                    holder0.editLayout = (LinearLayout)view.findViewById(R.id.translatedPrescriptionLinearLayout3);
                    holder0.crossButton = (ImageButton)view.findViewById(R.id.translatedPrescriptionImageButton2);
                    holder0.checkBox = (CheckBox)view.findViewById(R.id.translatedPrescriptionCheckBox1);

                    holder0.incrementDaysView = (TextView)view.findViewById(R.id.translatedPrescriptionTextView3);
                    holder0.decrementDaysView = (TextView)view.findViewById(R.id.translatedPrescriptionTextView4);

                    holder0.costView = (TextView)view.findViewById(R.id.translatedPrescriptionTextView2);

                    holder0.costView.setText(getItem(position).getPrescriptionDetails().getCost() + "/-");

                    if(getItem(position).getDisabled())
                        holder0.checkBox.setEnabled(false);

                    //The children are being set i.e. the medicines in the prescription
                    for(int i = 0;i< childCount;i++) {

                        holder0.linearLayouts.add(i, (LinearLayout) inflater.
                                inflate(R.layout.cards_child_prescription, null, false));
                        LinearLayout layout = holder0.linearLayouts.get(i);

                        if(getItem(position).getDisabled())
                            layout.findViewById(R.id.prescriptionChildCheckBox).setEnabled(false);

                        /*The items are added at index 1 to add them between
                        the tick button and the main layout
                          */
                        holder0.parentLayout.addView(layout, 0);
                    }
                    view.setTag(holder0);
                }
                else
                    holder0 = (ViewHolder0)view.getTag();


                holder0.checkBox.setChecked(getItem(position).getSelected());
                holder0.count = 0;

                if(isEditable){
                    holder0.editLayout.setVisibility(View.VISIBLE);
                    holder0.crossButton.setVisibility(View.VISIBLE);

                    for(int i = 0; i <childCount; i++){
                        final MedicineDetails medicineDetails = getItem(position).getPrescriptionDetails().getMedicineList().get(i);
                        final int childPos = i;

                        LinearLayout layout = holder0.linearLayouts.get(i);

                        ImageButton crossButton = (ImageButton)layout.findViewById(R.id.prescriptionChildImageButton1);
                        TextView incrementView = (TextView)layout.findViewById(R.id.prescriptionChildTextView2);
                        final TextView daysView = (TextView)layout.findViewById(R.id.prescriptionChildTextView3);
                        TextView decrementView = (TextView)layout.findViewById(R.id.prescriptionChildTextView4);
                        final TextView costView = (TextView)layout.findViewById(R.id.prescriptionChildTextView6);
                        TextView quantityPerDayView = (TextView)layout.findViewById(R.id.prescriptionChildTextView5);

                        quantityPerDayView.setText("x" + medicineDetails.getQuantityPerDay());

                        costView.setText(medicineDetails.getCost() * medicineDetails.getDays() * medicineDetails.getQuantityPerDay() + "/-");

                        daysView.setText(medicineDetails.getDays() + " days");

                        crossButton.setVisibility(View.VISIBLE);
                        incrementView.setVisibility(View.VISIBLE);
                        decrementView.setVisibility(View.VISIBLE);

                        crossButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context,""+childPos+"AA"+holder0.linearLayouts.size(),Toast.LENGTH_SHORT).show();
                                if(holder0.linearLayouts.size() == 1){
                                    itemDetailsList.remove(position);
                                }
                                holder0.linearLayouts.remove(childPos);
                                itemDetailsList.get(position).getPrescriptionDetails().getMedicineList().remove(childPos);
                                notifyDataSetChanged();
                            }
                        });

                        incrementView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (medicineDetails.getDays() + 1 < medicineDetails.getDaysSpecifiedInPrescription()) {

                                    medicineDetails.setDays(medicineDetails.getDays() + 1);
                                    daysView.setText(medicineDetails.getDays() + " days");

                                    float newCost = medicineDetails.getDays() * medicineDetails.getCost() * medicineDetails.getQuantityPerDay();

                                    costView.setText(newCost+ "/-");

                                    holder0.costView.setText(getItem(position).getPrescriptionDetails().getCost() + "/-");
                                }
                            }
                        });

                        decrementView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(medicineDetails.getDays() - 1 > 0){
                                    medicineDetails.setDays(medicineDetails.getDays() - 1);
                                    daysView.setText(medicineDetails.getDays() + " days");

                                    float newCost = medicineDetails.getDays() * medicineDetails.getCost() * medicineDetails.getQuantityPerDay();

                                    costView.setText(newCost+ "/-");

                                    holder0.costView.setText(getItem(position).getPrescriptionDetails().getCost() + "/-");
                                }
                            }
                        });

                    }
                }

                if(isSelectable){
                    holder0.checkBox.setVisibility(View.VISIBLE);
                    for(int i = 0;i<childCount;i++) {
                        final MedicineDetails medicineDetails = getItem(position).getPrescriptionDetails().getMedicineList().get(i);
                        LinearLayout layout = holder0.linearLayouts.get(i);

                        CheckBox checkBox = (CheckBox) layout.findViewById(R.id.prescriptionChildCheckBox);
                        checkBox.setVisibility(View.VISIBLE);
                        if(medicineDetails.getSelection())
                            holder0.count++;
                        checkBox.setChecked(medicineDetails.getSelection());

                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                medicineDetails.setSelection(isChecked);
                                if(isChecked)
                                    holder0.count++;
                                else
                                    holder0.count--;


                                if(holder0.count == 0) {
                                    getItem(position).setSelected(false);
                                    holder0.checkBox.setChecked(false);
                                }
                                else if(!getItem(position).getSelected() && holder0.count == 1){
                                    childChnage = true;
                                    getItem(position).setSelected(true);
                                    holder0.checkBox.setChecked(true);
                                }
                            }
                        });
                    }
                }

                if(isExpanded)
                    holder0.parentLayout.setVisibility(View.VISIBLE);

                //Events :

                holder0.incrementDaysView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                holder0.decrementDaysView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(int i = 0; i <childCount;i++){
                            LinearLayout layout = holder0.linearLayouts.get(i);

                            TextView decrementView = (TextView)layout.findViewById(R.id.prescriptionChildTextView4);
                            decrementView.callOnClick();

                        }
                    }
                });

                holder0.incrementDaysView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < childCount; i++) {
                            LinearLayout layout = holder0.linearLayouts.get(i);

                            TextView incrementView = (TextView) layout.findViewById(R.id.prescriptionChildTextView2);
                            incrementView.callOnClick();

                        }
                    }
                });

                holder0.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        getItem(position).setSelected(isChecked);
                        if(!childChnage){
                                for (int i = 0; i < childCount; i++) {
                                    itemDetailsList.get(position).getPrescriptionDetails().getMedicineList().get(i).setSelection(isChecked);
                                    CheckBox c = ((CheckBox) holder0.linearLayouts.get(i).findViewById(R.id.prescriptionChildCheckBox));
                                    c.setChecked(isChecked);
                                }
                            }
                        childChnage = false;
                    }
                });


                holder0.crossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailsList.remove(position);
                        notifyDataSetChanged();
                    }
                });


                holder0.minimizedLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder0.parentLayout.setVisibility(View.VISIBLE);
                        getItem(position).setExpanded(true);
                    }
                });

                holder0.checkImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder0.parentLayout.setVisibility(View.GONE);
                        getItem(position).setExpanded(false);
                        notifyDataSetChanged();
                    }
                });

                break;
            case 1:
                //TODO : make the edit text accept points i.e. bullets with every new line
                //TODO : make the prescription image clickable with a zoom activity opening as a response

                final PrescriptionDetails prescriptionDetails = getItem(position).getPrescriptionDetails();
                final ViewHolder1 holder1;
                if(view == null) {
                    view = inflater.inflate(R.layout.cards_prescription, parent, false);

                    holder1 = new ViewHolder1();

                    holder1.minimizedLayout = (LinearLayout) view.findViewById(R.id.prescriptionCardLinearLayout1);
                    holder1.maximizedLayout = (LinearLayout) view.findViewById(R.id.prescriptionCardLinearLayout2);

                    holder1.prescriptionImage = (ImageView) view.findViewById(R.id.prescriptionCardImageView1);
                    holder1.crossButton = (ImageButton) view.findViewById(R.id.prescriptionCardImageButton1);
                    holder1.checkButton = (ImageButton) view.findViewById(R.id.prescriptionCardImageButton2);
                    holder1.notesEditText = (EditText) view.findViewById(R.id.prescriptionCardEditText1);
                    holder1.daysPrescriptionTextView = (TextView) view.findViewById(R.id.prescriptionCardTextView1);
                    holder1.daysPicker = (NumberPicker) view.findViewById(R.id.prescriptionCardNumberPicker1);

                    view.setTag(holder1);
                }
                else
                     holder1 = (ViewHolder1)view.getTag();
                if(isExpanded) {
                    holder1.daysPicker.setVisibility(View.VISIBLE);
                    holder1.maximizedLayout.setVisibility(View.VISIBLE);
                }

                holder1.daysPicker.setMaxValue(100);
                holder1.daysPicker.setMinValue(0);

                int days = prescriptionDetails.getDays();
                holder1.daysPicker.setValue(days);
                holder1.notesEditText.setText(prescriptionDetails.getNotes());
                if(days == 0)
                    holder1.daysPrescriptionTextView.setText("Days : as Prescribed");
                else
                    holder1.daysPrescriptionTextView.setText("Days : " + days);

                holder1.prescriptionImage.setImageBitmap(prescriptionDetails.getThumbnail());

                //events :

                holder1.crossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailsList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                holder1.prescriptionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.replaceFragment(R.id.prescriptionCardImageButton1,prescriptionDetails.getImageUri());
                    }
                });

                holder1.daysPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        prescriptionDetails.setDays(newVal);

                        //displaying the days set by the number picker
                        if (newVal == 0)
                            holder1.daysPrescriptionTextView.setText("Days : as Prescribed");
                        else
                            holder1.daysPrescriptionTextView.setText("Days : " + newVal);
                    }
                });



                //expand the view for edit on clicking the minimized view
                holder1.minimizedLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder1.daysPicker.setVisibility(View.VISIBLE);
                        holder1.maximizedLayout.setVisibility(View.VISIBLE);
                        getItem(position).setExpanded(true);
                    }
                });

                holder1.checkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder1.daysPicker.setVisibility(View.GONE);
                        holder1.maximizedLayout.setVisibility(View.GONE);

                        //finalizing the details in the notes
                        prescriptionDetails.setNotes(holder1.notesEditText.getText().toString());
                        getItem(position).setExpanded(false);

                        notifyDataSetChanged();
                    }
                });

                break;

            case 2:
                final MedicineDetails medicineDetails = getItem(position).getMedicineDetails();
                final ViewHolder2 holder2;
                if(view == null) {
                    view = inflater.inflate(R.layout.cards_medicine, parent, false);
                    holder2 = new ViewHolder2();

                    holder2.minimizedLayout = (LinearLayout) view.findViewById(R.id.medicineCardLinearLayout2);
                    holder2.maximizedLayout = (LinearLayout) view.findViewById(R.id.medicineCardLinearLayout3);

                    holder2.checkBox = (CheckBox) view.findViewById(R.id.medicineCardCheckBox1);
                    holder2.nameOfMedicineTextView = (TextView) view.findViewById(R.id.medicineCardTextView1);
                    holder2.quantityOfMedicineTextView = (TextView) view.findViewById(R.id.medicineCardTextView2);
                    holder2.costOfMedicineTextView = (TextView) view.findViewById(R.id.medicineCardTextView3);
                    holder2.radioGroup = (RadioGroup)view.findViewById(R.id.medicineCardRadioGroup1);
                    holder2.typeOfMedicineImageView = (ImageView) view.findViewById(R.id.medicineCardImageView1);
                    holder2.crossButton = (ImageButton) view.findViewById(R.id.medicineCardImageButton1);
                    holder2.tabletRadioButton = (RadioButton) view.findViewById(R.id.medicineCardRadioButton1);
                    holder2.stripsRadioButton = (RadioButton) view.findViewById(R.id.medicineCardRadioButton2);
                    holder2.bottlesRadioButton = (RadioButton) view.findViewById(R.id.medicineCardRadioButton3);
                    holder2.quantityPicker = (NumberPicker) view.findViewById(R.id.medicineCardNumberPicker1);
                    holder2.checkButton = (ImageButton) view.findViewById(R.id.medicineCardImageButton2);

                    if(getItem(position).getDisabled())
                        holder2.checkBox.setEnabled(false);

                    view.setTag(holder2);
                }
                else
                    holder2 = (ViewHolder2)view.getTag();


                holder2.checkBox.setChecked(getItem(position).getSelected());
                holder2.nameOfMedicineTextView.setText(medicineDetails.getMedicineName());
                holder2.quantityOfMedicineTextView.setText(medicineDetails.getQuantity() + "");

                holder2.quantityPicker.setMaxValue(100);
                holder2.quantityPicker.setMinValue(1);
                holder2.quantityPicker.setValue(medicineDetails.getQuantity());

                if(isExpanded)
                    holder2.maximizedLayout.setVisibility(View.VISIBLE);

                if(isSelectable)
                    holder2.checkBox.setVisibility(View.VISIBLE);

                if(!isEditable)
                    holder2.crossButton.setVisibility(View.GONE);

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

                //TODO:set the cost of medicine
                holder2.quantityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        medicineDetails.setQuantity(newVal);

                        holder2.quantityOfMedicineTextView.setText(""+newVal);
                        holder2.costOfMedicineTextView.setText(""+(newVal * 20) + "/-");
                    }
                });

                holder2.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        getItem(position).setSelected(isChecked);
                    }
                });

                holder2.crossButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailsList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                holder2.checkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemDetailsList.get(position).setExpanded(false);
                        holder2.maximizedLayout.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }
                });

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
                            itemDetailsList.get(position).setExpanded(true);
                            holder2.maximizedLayout.setVisibility(View.VISIBLE);
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
}
