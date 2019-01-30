package com.example.rateaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private ListView listViewRestaurant;

    private List<Restaurant> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        wireWidgets();
        populateViews();
        setListeners();
    }

    private void setListeners() {
        listViewRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewRestaurantIntent = new Intent(RestaurantListActivity.this,
                        RestaurantActivity.class);
                viewRestaurantIntent.putExtra("editedRestaurant", restaurantList.get(position));
                startActivity(viewRestaurantIntent);
                finish();
            }
        });
    }

    private void populateViews() {
        Backendless.Data.of(Restaurant.class).find(new AsyncCallback<List<Restaurant>>() {
            @Override
            public void handleResponse(List<Restaurant> response) {
                restaurantList = response;
                RestaurantAdapter adapter = new RestaurantAdapter(RestaurantListActivity.this,
                        R.layout.item_restaurantlist, response);
                listViewRestaurant.setAdapter(adapter);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void wireWidgets() {
        listViewRestaurant = findViewById(R.id.listview_restaurants_list);
    }
}
