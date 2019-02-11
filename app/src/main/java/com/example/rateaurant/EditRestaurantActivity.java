package com.example.rateaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

public class EditRestaurantActivity extends AppCompatActivity {

    private TextView cancelView;
    private TextView finishView;
    private EditText nameField;
    private EditText styleField;
    private EditText addressField;
    private EditText websiteLinkField;
    private RatingBar ratingBar;
    private Spinner spinnerPrice;

    private Restaurant receivedRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        Intent receivedIntent = getIntent();
        receivedRestaurant = receivedIntent.getParcelableExtra("editedRestaurant");

        wireWidgets();
        populateViews(receivedRestaurant);
        setListeners();
    }

    private void wireWidgets() {
        cancelView = findViewById(R.id.textView_editrestaurant_cancel);
        finishView = findViewById(R.id.textView_editrestaurant_finish);
        nameField = findViewById(R.id.editText_editrestaurant_name);
        styleField = findViewById(R.id.editText_editrestaurant_style);
        addressField = findViewById(R.id.editText_editrestaurant_address);
        websiteLinkField = findViewById(R.id.editText_editrestaurant_link);
        ratingBar = findViewById(R.id.ratingBar_editrestaurant_rating);
        spinnerPrice = findViewById(R.id.spinner_editrestaurant_price);
    }

    private void populateViews(Restaurant restaurant) {
        nameField.setText(restaurant.getName());
        styleField.setText(restaurant.getStyle());
        addressField.setText(restaurant.getAddress());
        websiteLinkField.setText(restaurant.getWebsiteLink());
        ratingBar.setRating((float) restaurant.getRating());
    }

    private void setListeners() {

    }
}
