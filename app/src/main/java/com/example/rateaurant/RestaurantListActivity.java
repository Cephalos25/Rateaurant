package com.example.rateaurant;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private ListView listViewRestaurant;
    private FloatingActionButton fab;
    private TextView backButton;

    private List<Restaurant> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        wireWidgets();
        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        populateViews();
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
        registerForContextMenu(listViewRestaurant);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newRestaurantIntent = new Intent(RestaurantListActivity.this,
                        EditRestaurantActivity.class);
                newRestaurantIntent.putExtra("activity", "create");
                startActivity(newRestaurantIntent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(RestaurantListActivity.this,
                        LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
                startActivity(logoutIntent);

            }
        });
    }

    private void populateViews() {
        // refactor to only get the items from the current user
        // get the current user's objectId
        // make a data query and use the advanced object retrieval pattern to find all restaurants
        // with an ownerId matching the user's objectId

        String userId = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "ownerId = " + "'" + userId + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        Backendless.Data.of(Restaurant.class).find(queryBuilder, new AsyncCallback<List<Restaurant>>() {
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
        fab = findViewById(R.id.floatingActionButton_restaurantlist_add);
        backButton = findViewById(R.id.textView_restaurantlist_back);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_restaurantlist_delete, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.item_deletemenu_delete:
                deleteRestaurant(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteRestaurant(long id) {
        Backendless.Persistence.of(Restaurant.class).remove(restaurantList.get((int) id),
                new AsyncCallback<Long>() {
                    @Override
                    public void handleResponse(Long response) {

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                    }
                });
    }
}
