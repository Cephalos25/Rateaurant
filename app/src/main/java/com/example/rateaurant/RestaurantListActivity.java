package com.example.rateaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

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
        registerForContextMenu(listViewRestaurant);
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
