package com.app.drugcorner32.dc_template.Data;


import com.app.drugcorner32.dc_template.Helpers.helperIDGenerator;

import java.io.Serializable;

/**
 * Created by Tarun on 28-02-2015.
 *
 * Cost hasn't been incorporated yet neither in
 * constructor nor in getters
 *
 * TODO set limit on number of medicines in addQuantity
 * TODO cost , prescriptionDays etc have been set randomly
 */
public class MedicineDetails implements Serializable {

    public enum MedicineTypes{
        Tablets,Strips,Bottles
    }

    private int medicineID;

    private boolean isSelected = false;

    private String nameOfMedicine;
    private MedicineTypes medicineType;

    //This is required for medicine in the Translated Prescription
    //Also the days for which the medicine has been ordered cannot be more than
    // that specified in the prescription
    private int days = 1;
    private int daysSpecifiedInPrescription = (int)(Math.random()*10) + 3;

    //These are only available in medicine details that are a part of Prescription
    //otherwise they dont make sense
    private boolean isDisabled = false;

    //In case of repeating a previous order, quantity = previousQuantity
    private int quantity = 0;

    //This is the quantity with which the medicine was ordered in the previous order
    private int previousQuantity;

    private float cost = (int)(Math.random() * 50f);

    //copy constructor
    public MedicineDetails(MedicineDetails details){
        medicineID = details.getMedicineId();
        nameOfMedicine = details.getMedicineName();
        medicineType = details.getMedicineType();
        quantity = details.getQuantity();
        cost = details.getCost();
        quantity = details.getQuantity();
        daysSpecifiedInPrescription = details.getDaysSpecifiedInPrescription();
        days = details.getDays();
        previousQuantity = details.getPreviousQuantity();
    }

    public MedicineDetails(String name,MedicineTypes type,int previousQuantity){
        //temporary way of id ing the medicines
        medicineID = helperIDGenerator.getID();
        nameOfMedicine = name;
        medicineType = type;
        this.previousQuantity = previousQuantity;
    }

    public MedicineDetails(String name,MedicineTypes type){
        medicineID = helperIDGenerator.getID();
        nameOfMedicine = name;
        medicineType = type;
        quantity = 1;
    }

    public String getMedicineName(){
        return nameOfMedicine;
    }

    public MedicineTypes getMedicineType(){
        return medicineType;
    }

    public int getQuantity(){
        return quantity;
    }

    public boolean getSelection(){ return isSelected; }

    public int getMedicineId(){
        return medicineID;
    }

    public int getDays(){
        return days;
    }

    public int getDaysSpecifiedInPrescription(){
        return daysSpecifiedInPrescription;
    }

    public float getCost(){
        return  cost;
    }

    public boolean getDisabled(){
        return isDisabled;
    }

    public void setDisabled(boolean val){
        isDisabled = val;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public int getPreviousQuantity(){
        return previousQuantity;
    }

    public void setPreviousQuantity(int quantity){
        previousQuantity = quantity;
    }


    public void setDays(int val){
        days = val;
    }

    public void setDaysSpecifiedInPrescription(int val){
        daysSpecifiedInPrescription = val;
    }

    public void setSelection(boolean val){
        isSelected = val;
    }

    public void setQuantity(int quantity){
        if(quantity < 0)
            return;
        this.quantity = quantity;
    }

    public void setMedicineType(MedicineTypes types){
        medicineType = types;
    }

    @Override
    public boolean equals(Object o) {
        return (this.getMedicineId() == ((MedicineDetails)o).getMedicineId());
    }
}
