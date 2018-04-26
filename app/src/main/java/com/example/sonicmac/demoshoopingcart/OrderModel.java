package com.example.sonicmac.demoshoopingcart;

/**
 * Created by sonicmac on 07/04/18.
 */

public class OrderModel {

    private String orderName;
    private String orderDesc;
    private int orderPrice;
    //set default quantity 1
    private int itemQuantity = 1;

    public OrderModel(String orderName,String orderDesc,int orderPrice){
        this.orderName = orderName;
        this.orderDesc = orderDesc;
        this.orderPrice = orderPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

}
