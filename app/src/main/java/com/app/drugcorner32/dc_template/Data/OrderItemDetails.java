package com.app.drugcorner32.dc_template.Data;

/**
 * Created by Tarun on 10-03-2015.
 */
public class OrderItemDetails {

    public static enum TypesOfOrder {
        TRANSLATED_PRESCRIPTION,UNTRANSLATED_PRESCRIPTION,OTC
    }

    private boolean isSelected = false;

    private boolean isExpanded = false;

    private boolean isDisabled = false;

    private TypesOfOrder orderType;

    private PrescriptionDetails prescriptionDetails;

    private MedicineDetails medicineDetails;

    public OrderItemDetails(OrderItemDetails details,boolean isExpanded){
        this.isExpanded = isExpanded;
        this.orderType = details.getOrderType();
        if(orderType != TypesOfOrder.OTC) {
            this.prescriptionDetails = new PrescriptionDetails(details.getPrescriptionDetails());
        }
        else
            this.medicineDetails = new MedicineDetails(details.getMedicineDetails());
    }

    public OrderItemDetails(TypesOfOrder orderType,PrescriptionDetails prescriptionDetails,boolean isExpanded){
        this.isExpanded = isExpanded;
        this.orderType = orderType;
        this.prescriptionDetails = prescriptionDetails;
    }

    public OrderItemDetails(MedicineDetails medicineDetails,boolean isExpanded){
        this.isExpanded = isExpanded;
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

    public boolean getExpanded() { return isExpanded; }

    public void setExpanded(boolean val) { isExpanded = val; }

    public void setDisabled(boolean val){isDisabled = val;}

    public boolean getDisabled(){
        return isDisabled;
    }

    @Override
    public boolean equals(Object o) {
        OrderItemDetails details = (OrderItemDetails)o;
        if(orderType == details.getOrderType()) {
            if(orderType == TypesOfOrder.OTC)
                return medicineDetails.getMedicineId() == details.getMedicineDetails().getMedicineId();
            else
                return prescriptionDetails.getPrescriptionID() == details.getPrescriptionDetails().getPrescriptionID();
        }
        else
            return false;
    }
}
