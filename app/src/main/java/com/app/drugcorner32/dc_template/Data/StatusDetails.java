package com.app.drugcorner32.dc_template.Data;

import java.io.Serializable;

/**
 * Created by Tarun on 04-03-2015.
 * Shows all the possible status
 */

public class StatusDetails implements Serializable{

    public enum STATUSES {
        BEING_PROCESSED,RESEND_IMAGE,EDIT_MEDICINE,DELIVERED,CANCELLED,ON_THE_WAY,ORDER_NOT_PLACED_YET
    }

    private STATUSES currentStatus;

    public StatusDetails(STATUSES status){
        currentStatus = status;
    }

    public StatusDetails(){
        currentStatus = STATUSES.ORDER_NOT_PLACED_YET;
    }

    @Override
    public String toString() {
        switch (currentStatus){
            case BEING_PROCESSED:
                return "Order being processed";
            case DELIVERED:
                return "Delivered";
            case CANCELLED:
                return "Cancelled";
            case ON_THE_WAY:
                return "Order on the Way!";
            case ORDER_NOT_PLACED_YET:
                return "Order not placed yet";
            case EDIT_MEDICINE:
                return "Confirm order";
            case RESEND_IMAGE:
                return "Bad image";
            default:
                return "";
        }
    }

    public STATUSES getCurrentStatus(){
        return currentStatus;
    }
    public void setCurrentStatus(STATUSES status){
        currentStatus = status;
    }
}
