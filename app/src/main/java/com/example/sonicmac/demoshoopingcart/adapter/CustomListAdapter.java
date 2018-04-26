package com.example.sonicmac.demoshoopingcart.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sonicmac.demoshoopingcart.OrderModel;
import com.example.sonicmac.demoshoopingcart.R;
import com.example.sonicmac.demoshoopingcart.onClickListner;

import java.util.ArrayList;

/**
 * Created by sonicmac on 21/04/18.
 */

public class CustomListAdapter extends ArrayAdapter<OrderModel> {
    //to reference the Activity
    private final Activity context;
    //to store the animal images
    //to store the list of countries
    ArrayList<OrderModel> orderModelArrayList;
    onClickListner onClickListner;

    public CustomListAdapter(Context context,ArrayList<OrderModel> chapterObjectList) {
        super(context, R.layout.listview_row, chapterObjectList);
        this.context = (Activity) context;
        this.orderModelArrayList = chapterObjectList;
        this.onClickListner = ((onClickListner) context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);
        //this code gets references to objects in the listview_row.xml file

        TextView nameTextField = (TextView) rowView.findViewById(R.id.nameTextViewID);
        TextView infoTextField = (TextView) rowView.findViewById(R.id.infoTextViewID);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);
        final Button buttonAddToCart  = (Button) rowView.findViewById(R.id.buttonAddToCart);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(orderModelArrayList.get(position).getOrderName());

        infoTextField.setText("" +orderModelArrayList.get(position).getOrderPrice());

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListner.onButtonClick(position);
                buttonAddToCart.setText("Already Added");
            }
        });

        return rowView;

    }
}
