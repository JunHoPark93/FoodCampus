package com.jarvis.foodcampus.view.restaurant;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.model.RestaurantModel;
import com.jarvis.foodcampus.presenter.restaurant.RestaurantAdpater;
import com.jarvis.foodcampus.presenter.restaurant.RestaurantPresenterImpl;
import com.jarvis.foodcampus.presenter.restaurant.RestaurantPrestenter;
import com.jarvis.foodcampus.view.base.BaseActivity;

/**
 * Created by JunHo on 2016-10-22.
 */

public class RestaurantActivity extends BaseActivity implements RestaurantView, AdapterView.OnItemClickListener {

    private ListView listView;
    private RestaurantAdpater restaurantAdpater;
    private RestaurantPrestenter restaurantPrestenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        listView = (ListView) findViewById(R.id.restaurant_listview);
        restaurantAdpater = new RestaurantAdpater();

        listView.setAdapter(restaurantAdpater);

        restaurantPrestenter = new RestaurantPresenterImpl(this, getApplicationContext());
        restaurantPrestenter.initData();

        listView.setOnItemClickListener(this);
    }

    @Override
    public void add(Drawable icon, String str1, String str2) {
        restaurantAdpater.addItem(icon, str1, str2);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RestaurantModel pizzaViewItem = (RestaurantModel) parent.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(),"position"+position+"  "+pizzaViewItem.getTitleStr(), Toast.LENGTH_SHORT ).show();
    }
}
