package com.example.sonicmac.demoshoopingcart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonicmac.demoshoopingcart.adapter.CustomListAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Toolbar mTopToolbar;
    static int mNotifCount = 0;
    OrderController aController;
    String[] nameArray = {"Octopus","Pig","Sheep","Rabbit","Snake","Spider" };

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
    ArrayList<OrderModel> chapterObjectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);
        final Button secondBtn = (Button) findViewById(R.id.second);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);


        //Get Global Controller Class object (see application tag in AndroidManifest.xml)
        aController = (OrderController) getApplicationContext();

        OrderModel orderModel = null;

        for(int i=1;i<=4;i++)
        {
            int price = 10+i;
            // Create product model class object
            orderModel = new OrderModel("Product "+i,"Description "+i,price);
            //store product object to arraylist in controller
            aController.setOrderModels(orderModel);
        }

        /****** Create view elements dynamically and show on activity ******/

        //Product arraylist size
        int ProductsSize = aController.getOrderArrayListSize();

        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        /******** Dynamically create view elements - Start **********/
        for(int j = 0; j< ProductsSize; j++){
            String pName = aController.getProducts(j).getOrderName();
            int pPrice   = aController.getProducts(j).getOrderPrice();

            // Create LinearLayout to view elements
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            TextView product = new TextView(this);
            product.setText(" "+pName+"    ");

            //Add textView to LinearLayout
            ll.addView(product);

            TextView price = new TextView(this);
            price.setText("  $"+pPrice+"     ");

            //Add textView to LinearLayout
            ll.addView(price);

            final Button btn = new Button(this);
            btn.setId(j+1);
            btn.setText("Add To Cart");

            btn.setLayoutParams(params);

            final int index = j;

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Clicked button index
                    Log.i("TAG", "index :" + index);

                    OrderModel tempOrderObject = aController.getProducts(index);

                    if(!aController.getOrderCart().checkOrderInCart(tempOrderObject))
                    {
                        btn.setText("Added");
                        // Product not Exist in cart so add product to
                        // Cart product arraylist
                        aController.getOrderCart().setOrders(tempOrderObject);
                        Toast.makeText(getApplicationContext(), "Now Cart size: "+aController.getOrderCart().getCartSize(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //Cart product arraylist contains Product
                        Toast.makeText(getApplicationContext(), "Product "+(index+1)+" already added in cart.", Toast.LENGTH_LONG).show();
                    }
                }
            });

            //Add button to LinearLayout
            ll.addView(btn);

            //Add LinearLayout to XML layout
            lm.addView(ll);
        }

        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ChartActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);

        /*MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.badge_menu);
        RelativeLayout notifCount = (RelativeLayout)   MenuItemCompat.getActionView(item);

        TextView tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        int productSize = aController.getOrderCart().getCartSize();
        tv.setText(""+ productSize);*/

        MenuItem menuItem = menu.findItem(R.id.badge);
        menuItem.setIcon(buildCounterDrawable(aController.getOrderCart().getCartSize(),  R.drawable.shoppingcart));

        //Toast.makeText(this, "Count " + aController.getOrderCart().getCartSize(), Toast.LENGTH_SHORT).show();
        //TextView tv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
       //tv.setText("12");
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.badge){
            invalidateOptionsMenu();
            Intent i = new Intent(getBaseContext(), ChartActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.badge_menu, null);
        //view.setBackgroundResource(backgroundImageId);

        TextView textView = (TextView) view.findViewById(R.id.actionbar_notifcation_textview);
        textView.setText("" + count);
        //Toast.makeText(this, "count" +count, Toast.LENGTH_SHORT).show();


        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void increaseCounter(){
        if (aController.getOrderCart().getCartSize() != 0){
            invalidateOptionsMenu();
        }
    }
}
