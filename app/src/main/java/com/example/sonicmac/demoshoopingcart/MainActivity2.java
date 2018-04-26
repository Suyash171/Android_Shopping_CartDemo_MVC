package com.example.sonicmac.demoshoopingcart;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonicmac.demoshoopingcart.adapter.CustomListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by sonicmac on 21/04/18.
 */

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener,onClickListner{

    String[] nameArray = {"ABC","XYZ","PQR Sandhya","Rabbit","Snake","Spider" };

    String[] infoArray = {
            "8 tentacled monster",
            "Delicious in rolls",
            "Great for jumpers",
            "Nice in a stew",
            "Great for shoes",
            "Scary."
    };

    Integer[] imageArray = {R.drawable.book1,
            R.drawable.book2,
            R.drawable.book3,
            R.drawable.book4,
            R.drawable.book5,
            R.drawable.book6};

    ListView listView;
    Toolbar mTopToolbar;
    OrderController aController;
    OrderModel orderModel;
    ArrayList<OrderModel> orderModelArrayList;
    static Button notifCount;
    static int mNotifCount = 0;
    TextView textViewBadge;
    int mCartItemCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        aController = (OrderController) getApplicationContext();
        orderModelArrayList = new ArrayList<OrderModel>();

        for(int i=0;i < nameArray.length;i++)
        {
            int price = 10+i;
            // Create product model class object
            orderModel = new OrderModel(nameArray[i],infoArray[i],price);
            //store product object to arraylist in controller
            aController.setOrderModels(orderModel);
            orderModelArrayList.addAll(Arrays.asList(orderModel));
        }

        CustomListAdapter adapter = new CustomListAdapter(this,orderModelArrayList);

        listView = (ListView)findViewById(R.id.listviewID);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        //Product arraylist size
        int ProductsSize = aController.getOrderArrayListSize();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        OrderModel tempOrderObject = aController.getProducts(position);
        if(!aController.getOrderCart().checkOrderInCart(tempOrderObject))
        {
            // Product not Exist in cart so add product to
            // Cart product arraylist
            aController.getOrderCart().setOrders(tempOrderObject);
            Toast.makeText(getApplicationContext(), "Now Cart size: "+aController.getOrderCart().getCartSize(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Product "+(position+1)+" already added in cart.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item,menu);

        final MenuItem menuItem = menu.findItem(R.id.badge);

        View actionView = menuItem.getActionView();
        textViewBadge = (TextView) actionView.findViewById(R.id.actionbar_notifcation_textview);
        mCartItemCount = aController.getOrderCart().getCartSize();
        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        /*MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.badge_menu);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);

        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        int productSize = aController.getOrderCart().getCartSize();
        tv.setText(""+ productSize);*/


        //MenuItem menuItem = menu.findItem(R.id.badge);
       // menuItem.setIcon(buildCounterDrawable(aController.getOrderCart().getCartSize(),  R.drawable.shoppingcart));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.badge){
            Intent i = new Intent(getBaseContext(), ChartActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Hide unhide and increment the counter
     */
    private void setupBadge() {
        if (textViewBadge != null) {
            if (mCartItemCount == 0) {
                if (textViewBadge.getVisibility() != View.GONE) {
                    textViewBadge.setVisibility(View.GONE);
                }
            } else {
                textViewBadge.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textViewBadge.getVisibility() != View.VISIBLE) {
                    textViewBadge.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.badge_menu, null);
        //view.setBackgroundResource(backgroundImageId);

        TextView textView = (TextView) view.findViewById(R.id.actionbar_notifcation_textview);
        textView.setText("" + count);
        //Toast.makeText(this, "count" +count, Toast.LENGTH_SHORT).show();

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }


    @Override
    public void onButtonClick(int position) {
        OrderModel tempOrderObject = aController.getProducts(position);

        if(!aController.getOrderCart().checkOrderInCart(tempOrderObject))
        {
            // Product not Exist in cart so add product to
            // Cart product arraylist
            invalidateOptionsMenu();
            aController.getOrderCart().setOrders(tempOrderObject);
            //Toast.makeText(getApplicationContext(), "Now Cart size: "+aController.getOrderCart().getCartSize(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Product "+(position+1)+" already added in cart.", Toast.LENGTH_LONG).show();
        }
    }

}
