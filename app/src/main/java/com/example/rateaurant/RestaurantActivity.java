package com.example.rateaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private TextView backButton;

    private Restaurant receivedRestaurant = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent receivedIntent = getIntent();
        receivedRestaurant = receivedIntent.getParcelableExtra("editedRestaurant");

        wireWidgets();
        populateViews(receivedRestaurant);
        setListeners();
    }

    private void setListeners() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editRestaurantIntent = new Intent(RestaurantActivity.this,
                        EditRestaurantActivity.class);
                editRestaurantIntent.putExtra("editedRestaurant", receivedRestaurant);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void populateViews(Restaurant restaurant) {
        nameView.setText(restaurant.getName());
        styleView.setText(restaurant.getStyle());
        linkView.setText(restaurant.getWebsiteLink());
        addressView.setText(restaurant.getAddress());
        ratingBar.setRating((float) restaurant.getRating());
        String price = "";
        for (int i = 0; i < restaurant.getPrice(); i++) {
            price = price.concat("$");
        }
        priceView.setText(price);
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
        backButton = findViewById(R.id.textView_restaurant_back);
    }
}
