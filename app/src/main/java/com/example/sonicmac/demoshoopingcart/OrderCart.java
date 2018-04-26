package com.example.sonicmac.demoshoopingcart;

import java.util.ArrayList;

/**
 * Created by sonicmac on 07/04/18.
 */

public class OrderCart {

    int cartTotalAmount;
    private ArrayList<OrderModel> orderModelArrayList = new ArrayList<OrderModel>();

    public OrderModel getOrders(int position){
        return orderModelArrayList.get(position);
    }

    public void setOrders(OrderModel orders){
        orderModelArrayList.add(orders);
    }

    public int getCartSize(){
        return orderModelArrayList.size();
    }

    public boolean checkOrderInCart(OrderModel orderModel){
        return orderModelArrayList.contains(orderModel);
    }

    public int getCartTotalAmount() {
        return cartTotalAmount;
    }

    public void setCartTotalAmount(int cartTotalAmount) {
        this.cartTotalAmount = cartTotalAmount;
    }

}
