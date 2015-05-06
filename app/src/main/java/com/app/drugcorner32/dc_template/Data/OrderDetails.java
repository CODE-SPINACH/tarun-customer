package com.app.drugcorner32.dc_template.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Tarun on 04-03-2015.
 *
 * Contains the details of the current or previous order
 */
public class OrderDetails implements Serializable{

    private AddressDetails addressDetails;

    private boolean isSelected = false;

    private boolean isDisabled = false;

    private int orderNo;

    //The net cost of the order
    private float orderAmount;

    //Status of the delivery
    private StatusDetails orderStatus;

    //Date and time of the delivery
    private Date orderDate;

    //Prescriptions that were added
    private List<OrderItemDetails> orderItemDetailses = new ArrayList<>();

    public OrderDetails(){
        Calendar cal = Calendar.getInstance();
        orderNo = (int)(Math.random() * 1000);
        orderDate = cal.getTime();
        orderAmount = 0;
        addressDetails = null;
        orderStatus = new StatusDetails(StatusDetails.STATUSES.ORDER_NOT_PLACED_YET);
    }

    public OrderDetails(int orderNo,float amount,String address,StatusDetails status,Date date,
                        List<OrderItemDetails> detailses){
        this.orderNo = orderNo;
        orderAmount = amount;
        orderDate = date;
        orderStatus = status;
        orderItemDetailses = detailses;
    }

    public void setAddressDetails(AddressDetails details){
        addressDetails = details;
    }

    public AddressDetails getAddressDetails(){
        return addressDetails;
    }

    public int getOrderNo(){
        return orderNo;
    }


    public float getOrderAmount(){
        return orderAmount;
    }

    public Date getOrderDate(){
        return orderDate;
    }

    public StatusDetails getOrderStatus(){
        return orderStatus;
    }

    public List<OrderItemDetails> getOrderItemsList(){
        return orderItemDetailses;
    }

    public void setSelection(boolean val){
        isSelected = val;
    }

    public boolean getSelection(){ return isSelected; }

    public void setDisabled(boolean val){
        isDisabled = val;
    }

    public boolean getDisabled(){
        return isDisabled;
    }

}
