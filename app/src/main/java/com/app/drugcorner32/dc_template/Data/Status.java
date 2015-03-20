package com.app.drugcorner32.dc_template.Data;

/**
 * Created by Tarun on 04-03-2015.
 */
public class Status {

    public static enum STATUSES {
        BEING_PROCESSED,DELIVERED,CANCELLED,ON_THE_WAY,ORDER_NOT_PLACED_YET
    }

    private STATUSES currentStatus;

    public Status(STATUSES status){
        currentStatus = status;
    }

    public Status(){
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
