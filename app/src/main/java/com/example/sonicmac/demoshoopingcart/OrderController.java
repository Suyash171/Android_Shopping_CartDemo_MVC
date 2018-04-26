package com.example.sonicmac.demoshoopingcart;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by sonicmac on 07/04/18.
 */

public class OrderController extends Application{
    private ArrayList<OrderModel> orderModels = new ArrayList<OrderModel>();
    private OrderCart orderCart = new OrderCart();

    public OrderModel getProducts(int position){
        return orderModels.get(position);
    }

    public void setOrderModels(OrderModel orderModel){
        orderModels.add(orderModel);
    }

    public OrderCart getOrderCart(){
        return orderCart;
    }

    public int getOrderArrayListSize(){
        return orderModels.size();
    }

}
