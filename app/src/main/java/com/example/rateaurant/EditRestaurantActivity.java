package com.example.rateaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.Objects;

public class EditRestaurantActivity extends AppCompatActivity {
    private TextView cancelView;
    private TextView finishView;
    private EditText nameField;
    private EditText styleField;
    private EditText addressField;
    private EditText websiteLinkField;
    private RatingBar ratingBar;
    private SeekBar seekBarPrice;

    private Restaurant receivedRestaurant;
    private boolean activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        Intent receivedIntent = getIntent();
        receivedRestaurant = receivedIntent.getParcelableExtra("editedRestaurant");
        activity = Objects.equals(receivedIntent.getStringExtra("activity"), "create");

        wireWidgets();
        if(!activity) {
            populateViews(receivedRestaurant);
        }
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
        seekBarPrice = findViewById(R.id.seekBar_editrestaurant_price);
    }

    private void populateViews(Restaurant restaurant) {
        nameField.setText(restaurant.getName());
        styleField.setText(restaurant.getStyle());
        addressField.setText(restaurant.getAddress());
        websiteLinkField.setText(restaurant.getWebsiteLink());
        ratingBar.setRating((float) restaurant.getRating());
        seekBarPrice.setProgress(restaurant.getPrice());
    }

    private void setListeners() {
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!activity) {
                    Intent goBackIntent = new Intent(EditRestaurantActivity.this,
                            RestaurantActivity.class);
                    goBackIntent.putExtra("editedRestaurant", receivedRestaurant);
                    startActivity(goBackIntent);
                    finish();
                } else {
                    Intent goBackIntent = new Intent(EditRestaurantActivity.this,
                            RestaurantListActivity.class);
                    goBackIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(goBackIntent);
                    finish();
                }
            }
        });
        finishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!activity) {
                    receivedRestaurant.setName(nameField.getText().toString());
                    receivedRestaurant.setStyle(styleField.getText().toString());
                    receivedRestaurant.setAddress(addressField.getText().toString());
                    receivedRestaurant.setWebsiteLink(websiteLinkField.getText().toString());
                    receivedRestaurant.setRating(ratingBar.getRating());
                    receivedRestaurant.setPrice(seekBarPrice.getProgress());
                    Backendless.Persistence.of(Restaurant.class).save(receivedRestaurant, new AsyncCallback<Restaurant>() {
                        @Override
                        public void handleResponse(Restaurant response) {

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                    Intent goBackIntent = new Intent(EditRestaurantActivity.this,
                            RestaurantActivity.class);
                    goBackIntent.putExtra("editedRestaurant", receivedRestaurant);
                    startActivity(goBackIntent);
                    finish();
                } else {
                    Restaurant newRestaurant = new Restaurant();
                    newRestaurant.setName(nameField.getText().toString());
                    newRestaurant.setStyle(styleField.getText().toString());
                    newRestaurant.setAddress(addressField.getText().toString());
                    newRestaurant.setWebsiteLink(websiteLinkField.getText().toString());
                    newRestaurant.setRating(ratingBar.getRating());
                    newRestaurant.setPrice(seekBarPrice.getProgress());
                    Backendless.Data.of(Restaurant.class).save(newRestaurant, new AsyncCallback<Restaurant>() {
                        @Override
                        public void handleResponse(Restaurant response) {

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                    Intent goBackIntent = new Intent(EditRestaurantActivity.this,
                            RestaurantListActivity.class);
                    goBackIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(goBackIntent);
                    finish();
                }
            }
        });
    }
}
