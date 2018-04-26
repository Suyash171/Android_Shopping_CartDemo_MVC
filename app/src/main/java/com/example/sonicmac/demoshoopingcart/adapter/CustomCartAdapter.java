package com.example.sonicmac.demoshoopingcart.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonicmac.demoshoopingcart.BuildConfig;
import com.example.sonicmac.demoshoopingcart.OrderCart;
import com.example.sonicmac.demoshoopingcart.OrderController;
import com.example.sonicmac.demoshoopingcart.R;
import com.example.sonicmac.demoshoopingcart.cardInterface;
import com.example.sonicmac.demoshoopingcart.onClickListner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonicmac on 21/04/18.
 */

public class CustomCartAdapter extends ArrayAdapter<OrderCart> {
    private final Activity context;

    ArrayList<OrderCart> orderCartArrayList;
    static int counter = 0;
    cardInterface cardInterface;
    ArrayAdapter<Integer> Spinneradapter;

    public CustomCartAdapter(Context context,ArrayList<OrderCart> orderController,ArrayAdapter arrayAdapter) {
        super(context,R.layout.listview_row,orderController);
        this.context = (Activity) context;
        this.orderCartArrayList = orderController;
        this.Spinneradapter = arrayAdapter;
        this.cardInterface = ((cardInterface) context);
    }

    public class Holder {
        TextView nameTextField;
        TextView infoTextField;
        ImageView imageViewPlus;
        ImageView imageViewMinus;
        TextView textViewQuantity;
        Spinner spinnerQuanity;
        EditText editTextQuantity;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cart_list_item, null,true);

        holder.nameTextField = (TextView) rowView.findViewById(R.id.nameTextViewCartID);
        holder.infoTextField = (TextView) rowView.findViewById(R.id.infoTextViewCartID);
        holder.imageViewPlus = (ImageView)rowView.findViewById(R.id.plus);
        holder.imageViewMinus = (ImageView)rowView.findViewById(R.id.minus);
        holder.textViewQuantity = (TextView)rowView.findViewById(R.id.textviewQuantity);
        holder.spinnerQuanity = (Spinner)rowView.findViewById(R.id.spinnerQuanity);
        holder.editTextQuantity = (EditText)rowView.findViewById(R.id.editTextQuantity);

        String name = orderCartArrayList.get(position).getOrders(position).getOrderName();
        int quantity  = orderCartArrayList.get(position).getOrders(position).getItemQuantity();
        holder.editTextQuantity.setText(""+ quantity);

        final int price  = orderCartArrayList.get(position).getOrders(position).getOrderPrice();

        //this code sets the values of the objects to values from the arrays
        holder.nameTextField.setText(orderCartArrayList.get(position).getOrders(position).getOrderName());

        holder.infoTextField.setText(String.valueOf("Item Price : "+price));

        holder.spinnerQuanity.setAdapter(Spinneradapter);
        holder.spinnerQuanity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer orderPrice = orderCartArrayList.get(position).getOrders(position).getOrderPrice();
                //holder.infoTextField.setText();
                Integer value = parent.getSelectedItemPosition();
                double subTotal = 0;

                for(OrderCart p : orderCartArrayList) {
                    int quantity = value;
                    subTotal += p.getOrders(position).getOrderPrice() * quantity;
                }

                Toast.makeText(context, "Position " +subTotal, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //plus minus
        holder.imageViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //counter++;
              /*  int quantity = orderCartArrayList.get(position).getOrders(position).getItemQuantity();
                quantity++;
                //set quantity in model
                orderCartArrayList.get(position).getOrders(position).setItemQuantity(quantity);

                Integer subTotal = 0;
                subTotal = orderCartArrayList.get(position).getOrders(position).getOrderPrice() * quantity;
                holder.editTextQuantity.setText(""+quantity);
                holder.infoTextField.setText("Total Price : "+ subTotal);*/
                cardInterface.increaseItems(position);
                //Toast.makeText(context, "" +subTotal, Toast.LENGTH_SHORT).show();
                ///txtQty.Text = positive ? ++currentQty : --currentQty;
            }
        });

        holder.imageViewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* Integer subTotal = 0;
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
               holder.editTextQuantity.setText(""+quantity);
               holder.infoTextField.setText("Total Price : "+ subTotal);*/
                cardInterface.decreaseItems(position);
            }
        });

        return rowView;
    }
}
