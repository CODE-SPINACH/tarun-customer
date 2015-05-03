package com.app.drugcorner32.dc_template.Data;

import java.io.Serializable;

/**
 * Created by Tarun on 10-03-2015.
 */
public class OrderItemDetails implements Serializable{

    public static enum TypesOfOrder {
        PRESCRIPTION,OTC
    }

    public static int prescriptionCount = 0;

    private boolean isSelected = false;

    private boolean isDisabled = false;

    private TypesOfOrder orderType;

    private PrescriptionDetails prescriptionDetails;

    private MedicineDetails medicineDetails;

    public OrderItemDetails(OrderItemDetails details){
        this.orderType = details.getOrderType();
        if(orderType != TypesOfOrder.OTC) {
            this.prescriptionDetails = new PrescriptionDetails(details.getPrescriptionDetails());
        }
        else
            this.medicineDetails = new MedicineDetails(details.getMedicineDetails());
    }

    public OrderItemDetails(PrescriptionDetails prescriptionDetails){
        orderType = TypesOfOrder.PRESCRIPTION;
        this.prescriptionDetails = prescriptionDetails;
    }

    public OrderItemDetails(MedicineDetails medicineDetails,boolean isExpanded){
        this.orderType = TypesOfOrder.OTC;
        this.medicineDetails = medicineDetails;
    }

    public TypesOfOrder getOrderType(){
        return orderType;
    }

    public PrescriptionDetails getPrescriptionDetails(){
        return prescriptionDetails;
    }

    public MedicineDetails getMedicineDetails(){
        return medicineDetails;
    }

    public boolean getSelected(){
        return isSelected;
    }

    public void setSelected(boolean val){
        isSelected = val;
    }

    public float getCost(){
        if(orderType == TypesOfOrder.OTC)
            return medicineDetails.getCost() * medicineDetails.getQuantity();
        else
            return prescriptionDetails.getCost();
    }

    public void setDisabled(boolean val){isDisabled = val;}

    public boolean getDisabled(){
        return isDisabled;
    }

    @Override
    public boolean equals(Object o) {
        OrderItemDetails details = (OrderItemDetails)o;
        if(orderType == details.getOrderType()) {
            if(orderType == TypesOfOrder.OTC)
                return medicineDetails.equals(details.getMedicineDetails());
            else
                return prescriptionDetails.equals(details.getPrescriptionDetails());
        }
        else
            return false;
    }
}
