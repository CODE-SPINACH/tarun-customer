package com.app.drugcorner32.dc_template.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.drugcorner32.dc_template.Data.AddressDetails;
import com.app.drugcorner32.dc_template.Data.OrderDetails;
import com.app.drugcorner32.dc_template.Fragments.AddressFragment;
import com.app.drugcorner32.dc_template.R;

public class AddressActivity extends ActionBarActivity implements AddressFragment.Callback {

    private OrderDetails orderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        //getting the order details from the activity
        orderDetails = (OrderDetails)getIntent().getSerializableExtra("Order");
        Toast.makeText(this,orderDetails.getOrderItemsList().size() + "",Toast.LENGTH_SHORT).show();
        //setting up the toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.addressToolbar);
        TextView toolbarText = (TextView) findViewById(R.id.addressToolbarTextView1);
        ImageButton backButton = (ImageButton) findViewById(R.id.addressToolbarImageButton1);

        Typeface typeFace = Typeface.createFromAsset(toolbar.getContext().getAssets(), "fonts/gothic.ttf");
        toolbarText.setTypeface(typeFace);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container, AddressFragment.newInstance(), AddressFragment.TAG).commitAllowingStateLoss();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void startNotificationActivity(AddressDetails details){
        orderDetails.setAddressDetails(details);
        Intent intent = new Intent(this,NotificationActivity.class);
        intent.putExtra("Order",orderDetails);
        startActivity(intent);
    }

}
