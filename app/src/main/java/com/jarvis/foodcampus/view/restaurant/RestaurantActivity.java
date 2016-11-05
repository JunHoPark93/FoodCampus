package com.jarvis.foodcampus.view.restaurant;

import android.content.Intent;
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
import com.jarvis.foodcampus.view.detail.DetailActivity;

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
    public void add(RestaurantModel restaurantModel) {
        restaurantAdpater.addItem(restaurantModel);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RestaurantModel restaurantModel = (RestaurantModel) parent.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(),"position"+position+"  "+restaurantModel.getCategoryId(), Toast.LENGTH_SHORT ).show();

        // 나중엔 position 에따라 달라짐
        // 인텐트
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
        //finish(); 피니시를 해야될까
    }
}
