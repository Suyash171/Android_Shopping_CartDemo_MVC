package com.example.sonicmac.demoshoopingcart;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonicmac.demoshoopingcart.adapter.CustomCartAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sonicmac on 07/04/18.
 */

public class ChartActivity extends Activity implements AdapterView.OnItemClickListener,cardInterface{
    Spinner spinner;
    CustomCartAdapter cartAdapter;
    ArrayList<OrderCart> orderCartArrayList;
    ListView listView;
    Integer[] itemsarray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
    ArrayAdapter<Integer> spinnerAdapter;
    TextView textViewshowTotalAmount;
    Integer totalAmout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        spinner = (Spinner)findViewById(R.id.spinnerQuanity);
        listView = (ListView)findViewById(R.id.listviewCartID);
        textViewshowTotalAmount = (TextView)findViewById(R.id.showTotalAmount);

        TextView showCartContent  = (TextView) findViewById(R.id.showCart);
        final Button thirdBtn  = (Button) findViewById(R.id.third);

        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        final OrderController aController = (OrderController) getApplicationContext();

        // Get Cart Size
        final int cartSize = aController.getOrderCart().getCartSize();
        orderCartArrayList = new ArrayList<OrderCart>();

        String showString = "";

        spinnerAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,itemsarray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        /******** Show Cart Products on screen - Start ********/
        if (cartSize > 0){
            for (int i = 0; i<cartSize;i++){
                //Get product details
               /* String pName    = aController.getOrderCart().getOrders(i).getOrderName();
                int pPrice      = aController.getOrderCart().getOrders(i).getOrderPrice();
                String pDisc    = aController.getOrderCart().getOrders(i).getOrderDesc();
                showString += " Product Name : "+pName+" "+ "Price : "+pPrice+" "+ "Discription : "+pDisc+""+ " -----------------------------------\n\n";
               */
                orderCartArrayList.addAll(Arrays.asList(aController.getOrderCart()));
            }
        }else{
            showString = " Shopping cart is empty. ";
        }

        cartAdapter = new CustomCartAdapter(this,orderCartArrayList,spinnerAdapter);
        //showCartContent.setText(showString);
        listView.setAdapter(cartAdapter);
        //listView.setOnItemClickListener(this);

       double a = getTotalPrice(orderCartArrayList);
       textViewshowTotalAmount.setText("Total Payable Amount: " + String.valueOf(a));

        thirdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartSize > 0){
                    Intent i = new Intent(getBaseContext(), PaymentActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Shopping cart is empty.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Spinner Drop down elements

        // Creating adapter for spinner
       /* ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, modelCartListItems);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(this);*/

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Position" +position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartAdapter.notifyDataSetChanged();
    }

    //calculate total amount
  /*  public double getTotal(ArrayList<OrderCart> list){
        double total=0.0;
        for(int i=0;i<list.size();i++){
            total=total+Double.parseDouble(String.valueOf(list.get(i).getCartTotalAmount()));
        }
        return total;
    }*/

    @Override
    public void increaseItems(int position) {
        int quantity = orderCartArrayList.get(position).getOrders(position).getItemQuantity();
        quantity++;
        //set quantity in model
        orderCartArrayList.get(position).getOrders(position).setItemQuantity(quantity);

        Integer subTotal = 0;
        subTotal = orderCartArrayList.get(position).getOrders(position).getOrderPrice() * quantity;

       //orderCartArrayList.get(position).setCartTotalAmount(subTotal);

        double total = getTotalPrice(orderCartArrayList);
        textViewshowTotalAmount.setText("Total Payable Amount: " + String.valueOf(total));

        cartAdapter.notifyDataSetChanged();
        //holder.editTextQuantity.setText(""+quantity);
        //holder.infoTextField.setText("Total Price : "+ subTotal);
    }

    @Override
    public void decreaseItems(int position) {
        Integer subTotal = 0;
        //counter --;
        int quantity = orderCartArrayList.get(position).getOrders(position).getItemQuantity();
        quantity --;
        if (quantity < 1){
            quantity = 1;
        }
        //set quantity in model
        orderCartArrayList.get(position).getOrders(position).setItemQuantity(quantity);

        Integer orderPrice = orderCartArrayList.get(position).getOrders(position).getOrderPrice();
        subTotal = (orderPrice * quantity);
        //orderCartArrayList.get(position).setCartTotalAmount(subTotal);

        double total = getTotalPrice(orderCartArrayList);
        textViewshowTotalAmount.setText("Total Payable Amount: "+String.valueOf(total));

        cartAdapter.notifyDataSetChanged();
        //holder.editTextQuantity.setText(""+quantity);
        //holder.infoTextField.setText("Total Price : "+ subTotal);
    }

    //calculate total
    private double getTotalPrice(List<OrderCart> orderCartList){
        double total = 0.0;
        int quantity;
        for (int i = 0; i < orderCartList.size();i++){
            OrderCart cart = orderCartList.get(i);
            quantity = orderCartList.get(i).getOrders(i).getItemQuantity();
            total += cart.getOrders(i).getOrderPrice() * quantity;
            if (BuildConfig.DEBUG) Log.d("TAG","Position : " + i + "\tOriginal Price : "+cart.getOrders(i).getOrderPrice()+ "\t\tQuantity:\t" +quantity+"\t\tTotal Price " + total);
        }
        return total;
    }
}
