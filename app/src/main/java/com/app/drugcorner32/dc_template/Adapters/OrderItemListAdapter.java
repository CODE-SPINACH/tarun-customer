
package com.app.drugcorner32.dc_template.Adapters;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.drugcorner32.dc_template.Data.MedicineDetails;
import com.app.drugcorner32.dc_template.Data.OrderItemDetails;
import com.app.drugcorner32.dc_template.Data.PrescriptionDetails;
import com.app.drugcorner32.dc_template.Interfaces.OnFragmentChange;
import com.app.drugcorner32.dc_template.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tarun on 10-03-2015.
 *
 */

public class OrderItemListAdapter extends BaseExpandableListAdapter{

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public MedicineDetails getChild(int groupPosition, int childPosition) {
        return itemDetailsList.get(groupPosition).getPrescriptionDetails().getMedicineList().get(childPosition);
    }

    class ChildViewHolder {
        public LinearLayout layout;
        public TextView nameOfMedicineTextView;
        public TextView costOfMedicineTextView;
        public TextView tabletTextView;
        public TextView orTextView;
        public TextView stripTextView;
        public TextView numberOfMedicineTextView;
        public TextView removeTextView;
        public Button incrementButton;
        public Button decrementButton;
    }

    class GroupViewHolder0 {
        public ImageView prescriptionImage;
        public LinearLayout prescriptionLayout;
        public TextView costTextView;
        public TextView nameOfDrugsTextView;
        public TextView removeView;
    }

    class GroupViewHolder1 {
        public LinearLayout medicineLayout;
        public TextView nameOfMedicineTextView;
        public TextView costOfMedicineTextView;
        public TextView tabletTextView;
        public TextView orTextView;
        public TextView stripTextView;
        public TextView numberOfMedicineTextView;
        public TextView removeTextView;
        public Button incrementButton;
        public Button decrementButton;
    }

    private Context context;
    private OnFragmentChange callback1;
    private Callback callback2;
    private List<OrderItemDetails> itemDetailsList = new ArrayList<>();
    private boolean isRemovable;
    private boolean isEditable;
    private Typeface typeFace;
    private ExpandableListView expandableListView;

    public OrderItemListAdapter(Context context) {
        this.context = context;
        callback1 = (OnFragmentChange) context;
        callback2 = (Callback) context;
        typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf");
    }

    public void setRemovable(boolean val){
        isRemovable = val;
    }

    public void setEditable(boolean val){
        isEditable = val;
    }


    public void setItemDetailsList(List<OrderItemDetails> list){
        itemDetailsList = list;
    }

    public List<OrderItemDetails> getItemDetailsList(){ return itemDetailsList; }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public int getGroupCount() {
        return itemDetailsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(getGroupType(groupPosition) == 0)
            if(getGroup(groupPosition).getPrescriptionDetails().getPrescriptionType() == PrescriptionDetails.TypesOfPrescription.TRANSLATED_PRESCRIPTION)
                return itemDetailsList.get(groupPosition).getPrescriptionDetails().getMedicineList().size();
        return 0;
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, final View convertView, final ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        int viewType = getGroupType(groupPosition);
        switch (viewType){
            case 0:
                final GroupViewHolder0 holder0;
                final PrescriptionDetails prescriptionDetails1 = getGroup(groupPosition).getPrescriptionDetails();
                final int childCount = prescriptionDetails1.getMedicineList().size();

                if(view == null) {
                    holder0 = new GroupViewHolder0();
                    view = inflater.inflate(R.layout.cards_prescription, parent, false);

                    //The horizontal layout on whose touch event the children are to be shown
                    holder0.prescriptionLayout = (LinearLayout) view.findViewById(R.id.prescriptionCardLinearLayout1);

                    holder0.prescriptionImage = (ImageView)view.findViewById(R.id.prescriptionCardImageView1);
                    holder0.costTextView = (TextView)view.findViewById(R.id.prescriptionCardTextView1);
                    holder0.nameOfDrugsTextView = (TextView)view.findViewById(R.id.prescriptionCardTextView2);
                    holder0.removeView = (TextView) view.findViewById(R.id.prescriptionCardTextView3);

                    holder0.nameOfDrugsTextView.setTypeface(typeFace);
                    holder0.costTextView.setTypeface(typeFace);
                    holder0.removeView.setTypeface(typeFace);

                    if(!isRemovable)
                        holder0.removeView.setVisibility(View.GONE);

                    view.setTag(holder0);
                }
                else
                    holder0 = (GroupViewHolder0)view.getTag();

                String appendedMedicineNames = "";

                if(prescriptionDetails1.getPrescriptionType() == PrescriptionDetails.TypesOfPrescription.TRANSLATED_PRESCRIPTION) {
                    for (int i = 0; i < childCount; i++) {
                        MedicineDetails details = prescriptionDetails1.getMedicineList().get(i);
                        appendedMedicineNames += (details.getMedicineName() + ",");
                    }
                    if (appendedMedicineNames.length() > 0)
                        appendedMedicineNames = appendedMedicineNames.substring(0, appendedMedicineNames.length() - 1);
                }
                else {
                    appendedMedicineNames = "Prescription not translated yet";
                    holder0.costTextView.setVisibility(View.GONE);
                }
                //TODO change this on finalization
                if(prescriptionDetails1.getPrescriptionType() == PrescriptionDetails.TypesOfPrescription.UNTRANSLATED_PRESCRIPTION)
                    holder0.prescriptionImage.setImageURI(prescriptionDetails1.getThumbnailUri());
                else
                    holder0.prescriptionImage.setImageBitmap(null);

                holder0.nameOfDrugsTextView.setText(appendedMedicineNames);
                holder0.costTextView.setText(prescriptionDetails1.getCost()+"/-");

                //Events :
                holder0.removeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for(MedicineDetails details : prescriptionDetails1.getMedicineList())
                            details.setQuantity(0);
                        expandableListView.collapseGroup(groupPosition);
                        collapse(holder0.prescriptionLayout, groupPosition);
                    }
                });

                holder0.prescriptionImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback1.replaceFragment(R.id.prescriptionCardImageView1,prescriptionDetails1.getImageUri());
                    }
                });

                break;

            case 1:
                //TODO set check on the max number of medicines quantity that are allowed
                final MedicineDetails medicineDetails = getGroup(groupPosition).getMedicineDetails();
                final GroupViewHolder1 holder1;
                if(view == null) {
                    view = inflater.inflate(R.layout.cards_medicine, parent, false);
                    holder1 = new GroupViewHolder1();

                    holder1.medicineLayout = (LinearLayout) view.findViewById(R.id.medicineCardLinearLayout1);
                    holder1.nameOfMedicineTextView = (TextView) view.findViewById(R.id.medicineCardTextView1);
                    holder1.costOfMedicineTextView = (TextView) view.findViewById(R.id.medicineCardTextView2);
                    holder1.tabletTextView = (TextView) view.findViewById(R.id.medicineCardTextView3);
                    holder1.orTextView = (TextView) view.findViewById(R.id.medicineCardTextView4);
                    holder1.stripTextView = (TextView) view.findViewById(R.id.medicineCardTextView5);
                    holder1.numberOfMedicineTextView = (TextView) view.findViewById(R.id.medicineCardTextView6);
                    holder1.removeTextView = (TextView) view.findViewById(R.id.medicineCardTextView7);
                    holder1.decrementButton = (Button) view.findViewById(R.id.medicineCardButton1);
                    holder1.incrementButton = (Button) view.findViewById(R.id.medicineCardButton2);

                    holder1.numberOfMedicineTextView.setTypeface(typeFace);
                    holder1.removeTextView.setTypeface(typeFace);
                    holder1.stripTextView.setTypeface(typeFace);
                    holder1.orTextView.setTypeface(typeFace);
                    holder1.tabletTextView.setTypeface(typeFace);
                    holder1.costOfMedicineTextView.setTypeface(typeFace);
                    holder1.nameOfMedicineTextView.setTypeface(typeFace);
                    holder1.decrementButton.setTypeface(typeFace);
                    holder1.incrementButton.setTypeface(typeFace);


                    if(!isRemovable)
                        holder1.removeTextView.setVisibility(View.INVISIBLE);

                    if(!isEditable){
                        holder1.incrementButton.setVisibility(View.INVISIBLE);
                        holder1.decrementButton.setVisibility(View.INVISIBLE);
                    }

                    view.setTag(holder1);
                }
                else
                    holder1 = (GroupViewHolder1)view.getTag();


                holder1.nameOfMedicineTextView.setText(medicineDetails.getMedicineName());
                holder1.costOfMedicineTextView.setText(medicineDetails.getCost() * medicineDetails.getQuantity() + "/-");
                holder1.numberOfMedicineTextView.setText(medicineDetails.getQuantity() + "");

                holder1.orTextView.setVisibility(View.VISIBLE);
                holder1.stripTextView.setVisibility(View.VISIBLE);
                holder1.tabletTextView.setText("tablet");

                if(medicineDetails.getMedicineType() == MedicineDetails.MedicineTypes.Strips) {
                    holder1.stripTextView.setTextColor(Color.parseColor("#2E9C58"));
                    holder1.tabletTextView.setTextColor(Color.parseColor("#777777"));
                }
                else if(medicineDetails.getMedicineType() == MedicineDetails.MedicineTypes.Tablets) {
                    holder1.tabletTextView.setTextColor(Color.parseColor("#2E9C58"));
                    holder1.stripTextView.setTextColor(Color.parseColor("#777777"));
                }
                else if(medicineDetails.getMedicineType() == MedicineDetails.MedicineTypes.Bottles){
                    holder1.orTextView.setVisibility(View.GONE);
                    holder1.stripTextView.setVisibility(View.GONE);
                    holder1.tabletTextView.setText("bottle");
                }

                if(isEditable) {
                    holder1.tabletTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            medicineDetails.setMedicineType(MedicineDetails.MedicineTypes.Tablets);
                            if (isRemovable)
                                callback2.updateCart(null);
                            holder1.tabletTextView.setTextColor(Color.parseColor("#2E9C58"));
                            holder1.stripTextView.setTextColor(Color.parseColor("#777777"));
                        }
                    });

                    holder1.stripTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            medicineDetails.setMedicineType(MedicineDetails.MedicineTypes.Strips);
                            if (isRemovable)
                                callback2.updateCart(null);
                            holder1.stripTextView.setTextColor(Color.parseColor("#2E9C58"));
                            holder1.tabletTextView.setTextColor(Color.parseColor("#777777"));
                        }
                    });

                    holder1.incrementButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int quantity = medicineDetails.getQuantity() + 1;
                            medicineDetails.setQuantity(quantity);
                            holder1.costOfMedicineTextView.setText(medicineDetails.getCost() * quantity + "/-");
                            holder1.numberOfMedicineTextView.setText("" + quantity);
                            if (isRemovable) {
                                if (quantity == 1)
                                    callback2.updateCart(itemDetailsList.get(groupPosition));
                                else
                                    callback2.updateCart(null);
                            }
                            callback2.updateBottomMenu();
                        }
                    });

                    holder1.decrementButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int quantity = medicineDetails.getQuantity() - 1;
                            medicineDetails.setQuantity(quantity);
                            holder1.costOfMedicineTextView.setText(medicineDetails.getCost() * quantity + "/-");
                            holder1.numberOfMedicineTextView.setText("" + quantity);

                            if (isRemovable) {
                                if (quantity == 0)
                                    callback2.updateCart(itemDetailsList.get(groupPosition));
                                else
                                    callback2.updateCart(null);
                            }
                            callback2.updateBottomMenu();
                        }
                    });
                }

                holder1.removeTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        medicineDetails.setQuantity(0);
                        collapse(holder1.medicineLayout,groupPosition);
                    }
                });
                break;
        }
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        final MedicineDetails medicineDetails = getGroup(groupPosition).getPrescriptionDetails().getMedicineList().get(childPosition);
        final ChildViewHolder childHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.cards_child_prescription, parent, false);
            childHolder = new ChildViewHolder();

            childHolder.layout = (LinearLayout) convertView.findViewById(R.id.childMedicineCardLinearLayout1);
            childHolder.nameOfMedicineTextView = (TextView) convertView.findViewById(R.id.childMedicineCardTextView1);
            childHolder.costOfMedicineTextView = (TextView) convertView.findViewById(R.id.childMedicineCardTextView2);
            childHolder.tabletTextView = (TextView) convertView.findViewById(R.id.childMedicineCardTextView3);
            childHolder.orTextView = (TextView) convertView.findViewById(R.id.childMedicineCardTextView4);
            childHolder.stripTextView = (TextView) convertView.findViewById(R.id.childMedicineCardTextView5);
            childHolder.numberOfMedicineTextView = (TextView) convertView.findViewById(R.id.childMedicineCardTextView6);
            childHolder.removeTextView = (TextView) convertView.findViewById(R.id.childMedicineCardTextView7);
            childHolder.decrementButton = (Button) convertView.findViewById(R.id.childMedicineCardButton1);
            childHolder.incrementButton = (Button) convertView.findViewById(R.id.childMedicineCardButton2);

            childHolder.numberOfMedicineTextView.setTypeface(typeFace);
            childHolder.removeTextView.setTypeface(typeFace);
            childHolder.stripTextView.setTypeface(typeFace);
            childHolder.orTextView.setTypeface(typeFace);
            childHolder.tabletTextView.setTypeface(typeFace);
            childHolder.costOfMedicineTextView.setTypeface(typeFace);
            childHolder.nameOfMedicineTextView.setTypeface(typeFace);
            childHolder.decrementButton.setTypeface(typeFace);
            childHolder.incrementButton.setTypeface(typeFace);

            if (!isRemovable)
                childHolder.removeTextView.setVisibility(View.INVISIBLE);

            if(!isEditable) {
                childHolder.incrementButton.setVisibility(View.INVISIBLE);
                childHolder.decrementButton.setVisibility(View.INVISIBLE);
            }

            convertView.setTag(childHolder);
        } else
            childHolder = (ChildViewHolder) convertView.getTag();

        childHolder.nameOfMedicineTextView.setText(medicineDetails.getMedicineName());
        childHolder.costOfMedicineTextView.setText(medicineDetails.getCost() * medicineDetails.getQuantity() + "");
        childHolder.numberOfMedicineTextView.setText(medicineDetails.getQuantity() + "");
        childHolder.orTextView.setVisibility(View.VISIBLE);
        childHolder.stripTextView.setVisibility(View.VISIBLE);
        childHolder.tabletTextView.setText("tablet");


        if (medicineDetails.getMedicineType() == MedicineDetails.MedicineTypes.Strips) {
            childHolder.stripTextView.setTextColor(Color.parseColor("#2E9C58"));
            childHolder.tabletTextView.setTextColor(Color.parseColor("#777777"));
        } else if (medicineDetails.getMedicineType() == MedicineDetails.MedicineTypes.Tablets) {
            childHolder.tabletTextView.setTextColor(Color.parseColor("#2E9C58"));
            childHolder.stripTextView.setTextColor(Color.parseColor("#777777"));
        } else {
            childHolder.orTextView.setVisibility(View.GONE);
            childHolder.stripTextView.setVisibility(View.GONE);
            childHolder.tabletTextView.setText("bottle");
        }

        if (isEditable) {

            childHolder.tabletTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    medicineDetails.setMedicineType(MedicineDetails.MedicineTypes.Tablets);
                    childHolder.tabletTextView.setTextColor(Color.parseColor("#2E9C58"));
                    childHolder.stripTextView.setTextColor(Color.parseColor("#777777"));
                    if (isRemovable)
                        callback2.updateCart(null);
                }
            });

            childHolder.stripTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    medicineDetails.setMedicineType(MedicineDetails.MedicineTypes.Strips);
                    childHolder.stripTextView.setTextColor(Color.parseColor("#2E9C58"));
                    childHolder.tabletTextView.setTextColor(Color.parseColor("#777777"));
                    if (isRemovable)
                        callback2.updateCart(null);
                }
            });

            childHolder.incrementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = medicineDetails.getQuantity() + 1;
                    medicineDetails.setQuantity(quantity);

                    if (isRemovable) {
                        if (quantity == 1)
                            callback2.updateCart(getGroup(groupPosition));
                        else
                            callback2.updateCart(null);
                    }
                    callback2.updateBottomMenu();
                    childHolder.costOfMedicineTextView.setText(medicineDetails.getCost() * quantity + "/-");
                    childHolder.numberOfMedicineTextView.setText("" + quantity);
                    notifyDataSetChanged();
                }
            });

            childHolder.decrementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = medicineDetails.getQuantity() - 1;
                    if (quantity < 0)
                        return;

                    if (isRemovable) {
                        if (quantity == 0) {
                            medicineDetails.setQuantity(quantity);
                            callback2.updateCart(getGroup(groupPosition));
                        } else
                            callback2.updateCart(null);
                    } else if (quantity == 0)
                        return;

                    medicineDetails.setQuantity(quantity);
                    callback2.updateBottomMenu();
                    childHolder.costOfMedicineTextView.setText(medicineDetails.getCost() * quantity + "/-");
                    childHolder.numberOfMedicineTextView.setText("" + quantity);
                    notifyDataSetChanged();
                }
            });
        }
        childHolder.removeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicineDetails.setQuantity(0);
                //This ensures that if all the medicines have been removed then the prescription gets removed too
                boolean delete = true;
                for (MedicineDetails details : getGroup(groupPosition).getPrescriptionDetails().getMedicineList())
                    if (details.getQuantity() != 0)
                        delete = false;
                if (delete) {
                    itemDetailsList.remove(groupPosition);
                    notifyDataSetChanged();
                }

                View parentView = getGroupView(groupPosition,true,null,null);
                GroupViewHolder0 holder0 = (GroupViewHolder0)parentView.getTag();

                expandableListView.collapseGroup(groupPosition);
                collapse(holder0.prescriptionLayout, groupPosition);
                callback2.updateBottomMenu();
                notifyDataSetChanged();
            }
        });
        return convertView;

    }

    @Override
    public int getGroupType(int groupPosition) {
        switch (itemDetailsList.get(groupPosition).getOrderType())
        {
            case PRESCRIPTION:
                return 0;
            case OTC:
                return 1;
            default:
                return 0;
        }

    }

    public void add(OrderItemDetails item){
        itemDetailsList.add(item);
    }

    @Override
    public OrderItemDetails getGroup(int groupPosition) {
        return itemDetailsList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    private ValueAnimator slideAnimator(final LinearLayout mLinearLayout,int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private void collapse(final LinearLayout mLinearLayout, final int position) {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(mLinearLayout,finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                mLinearLayout.setLayoutParams(layoutParams);

                itemDetailsList.remove(position);
                notifyDataSetChanged();
                callback2.updateBottomMenu();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationStart(Animator animation) {

            }
        });
        mAnimator.start();
    }

    public void setExpandableListView(ExpandableListView listView){
        expandableListView = listView;
    }

    public interface Callback{
        void updateCart(OrderItemDetails details);
        void updateBottomMenu();
    }

}
