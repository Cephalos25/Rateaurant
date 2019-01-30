package com.example.rateaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView styleView;
    private TextView linkIndicator;
    private TextView linkView;
    private TextView addressIndicator;
    private TextView addressView;
    private RatingBar ratingBar;
    private TextView priceView;
    private TextView editButton;

    private Intent receivedIntent;
    private Restaurant receivedRestaurant = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        receivedIntent = getIntent();
        receivedRestaurant = receivedIntent.getParcelableExtra("editedRestaurant");

        wireWidgets();
    }

    private void wireWidgets() {
        nameView = findViewById(R.id.textView_restaurant_name);
        styleView = findViewById(R.id.textView_restaurant_style);
        linkIndicator = findViewById(R.id.textView_restaurant_websiteindicator);
        linkView = findViewById(R.id.textView_restaurant_websitelink);
        addressIndicator = findViewById(R.id.textView_restaurant_addressindicator);
        addressView = findViewById(R.id.textView_restaurant_address);
        ratingBar = findViewById(R.id.ratingBar_restaurant_rating);
        priceView = findViewById(R.id.textView_restaurant_price);
        editButton = findViewById(R.id.textView_restaurant_edit);
    }
}
