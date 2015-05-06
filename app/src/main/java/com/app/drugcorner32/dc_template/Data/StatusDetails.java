package com.app.drugcorner32.dc_template.Data;

import java.io.Serializable;

/**
 * Created by Tarun on 04-03-2015.
 */
public class StatusDetails implements Serializable{

    public static enum STATUSES {
        BEING_PROCESSED,DELIVERED,CANCELLED,ON_THE_WAY,ORDER_NOT_PLACED_YET
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
                return "Being Processed";
            case DELIVERED:
                return "Delivered";
            case CANCELLED:
                return "Cancelled";
            case ON_THE_WAY:
                return "Order on the Way!";
            case ORDER_NOT_PLACED_YET:
                return "You haven't placed the order yet!!";
            default:
                return "";
        }
    }
}
