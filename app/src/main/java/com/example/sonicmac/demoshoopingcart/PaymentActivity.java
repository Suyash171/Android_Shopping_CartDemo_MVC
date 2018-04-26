package com.example.sonicmac.demoshoopingcart;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by sonicmac on 07/04/18.
 */

public class PaymentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payment_layout);

        TextView showCartContent = (TextView) findViewById(R.id.showCart);
        final OrderController orderController = (OrderController) getApplicationContext();
        int cartSize = orderController.getOrderCart().getCartSize();

        String showString = "";

        for (int i = 0; i<cartSize;i++){
            String orderName = orderController.getOrderCart().getOrders(i).getOrderName();
            int quantity = orderController.getOrderCart().getOrders(i).getItemQuantity(); //need to use Db something to store values
            int price = orderController.getOrderCart().getOrders(i).getOrderPrice() * quantity;
            String orderDesc = orderController.getOrderCart().getOrders(i).getOrderDesc();
            showString += "Product Name : "+orderName+" "+ "Price : "+price+" "+ "Discription : "+orderDesc+""+ " -----------------------------------\n\n\n";
        }
        showCartContent.setText(showString);
    }
}
