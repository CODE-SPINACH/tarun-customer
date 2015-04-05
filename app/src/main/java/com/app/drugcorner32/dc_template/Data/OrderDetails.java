package com.app.drugcorner32.dc_template.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarun on 04-03-2015.
 *
 * Contains the details of the current or previous order
 */
public class OrderDetails implements Serializable{

    private boolean isSelected = false;

    private boolean isDisabled = false;

    private int orderNo;

    //The net cost of the order
    private float orderAmount;

    //The address to where the delivery was made
    private String orderDeliveryAddress;

    //Status of the delivery
    private Status orderStatus;

    //Date and time of the delivery
    private int orderDate;

    //Prescriptions that were added
    private List<OrderItemDetails> orderItemDetailses = new ArrayList<>();

    public OrderDetails(int orderNo,float amount,String address,Status status,int date,
                        List<OrderItemDetails> detailses){
        this.orderNo = orderNo;
        orderAmount = amount;
        orderDeliveryAddress = address;
        orderDate = date;
        orderStatus = status;
        orderItemDetailses = detailses;
    }

    public int getOrderNo(){
        return orderNo;
    }

    public String getOrderDeliveryAddress(){
        return orderDeliveryAddress;
    }

    public float getOrderAmount(){
        return orderAmount;
    }

    public int getOrderDate(){
        return orderDate;
    }

    public Status getOrderStatus(){
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
