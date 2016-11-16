package com.jarvis.foodcampus.view.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
    private String res;
    private String s_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        listView = (ListView) findViewById(R.id.restaurant_listview);
        restaurantAdpater = new RestaurantAdpater();
        listView.setAdapter(restaurantAdpater);

        /**
         *  인텐트 받는부분인데 뷰에서 하면 안되는 기능인데 어떻게 빼지
         *  /////////////////////////////////////////////////
         */
        Intent intentGet = getIntent();
        res = intentGet.getStringExtra("restaurant");
        TextView resBarTv = (TextView) findViewById( R.id.restautant_bar_tv);
        if(res.toString().equals("search")){
            s_content = intentGet.getStringExtra("searchContent");
            resBarTv.setText("Search : "+s_content);

        }else{
            resBarTv.setText(res);
        }
        /**
         *  여기까지
         *  /////////////////////////////////////////
         */


        restaurantPrestenter = new RestaurantPresenterImpl(this, getApplicationContext());
        restaurantPrestenter.whichRestaurant(res);

        restaurantPrestenter.initData(s_content);

        System.out.println(restaurantAdpater.getCount()+"레스토랑뷰사이즈");
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
        intent.putExtra("restaurantId",String.valueOf(restaurantModel.getRestaurantId())); // 넘어갈때 레스토랑id 같이 넘김
        intent.putExtra("categoryId",res); // 디테일액티비티에서 그림아이콘 설정하기 위해서 넘김
        startActivity(intent);
        //finish(); //피니시를 해야될까
    }
}
