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

    //number of medicine to be taken per day
    private int quantityPerDay = (int)(Math.random() * 10) + 1;

    private int quantity;

    private float cost = (int)(Math.random() * 50f);

    //copy constructor
    public MedicineDetails(MedicineDetails details){
        medicineID = details.getMedicineId();
        nameOfMedicine = details.getMedicineName();
        medicineType = details.getMedicineType();
        quantity = details.getQuantity();
        quantityPerDay = details.getQuantityPerDay();
        cost = details.getCost();
        quantity = details.getQuantity();
        daysSpecifiedInPrescription = details.getDaysSpecifiedInPrescription();
        days = details.getDays();
    }

    public MedicineDetails(String name,MedicineTypes type,int quantity){
        //temporary way of id ing the medicines
        medicineID = helperIDGenerator.getID();
        nameOfMedicine = name;
        medicineType = type;
        this.quantity = quantity;
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

    public int getQuantityPerDay(){
        return quantityPerDay;
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


    public void setQuantityPerDay(int val){
        quantityPerDay = val;
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
