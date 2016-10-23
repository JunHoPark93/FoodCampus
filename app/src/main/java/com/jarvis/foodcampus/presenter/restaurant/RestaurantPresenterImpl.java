package com.jarvis.foodcampus.presenter.restaurant;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.view.restaurant.RestaurantView;

/**
 * Created by JunHo on 2016-10-22.
 */

public class RestaurantPresenterImpl implements RestaurantPrestenter {

    private RestaurantView restaurantView;
    private Context context;

    public RestaurantPresenterImpl(RestaurantView restaurantView, Context context) {
        this.restaurantView = restaurantView;
        this.context = context;
    }

    /**
     *  어떤 데이터를 붙일지 서버에서 가져올 것
     */
    public void initData() {
        restaurantView.add(ContextCompat.getDrawable(context, R.drawable.nene),"네네치킨", "호롤로");
        restaurantView.add(ContextCompat.getDrawable(context, R.drawable.wang),"왕서방치킨피자", "호롤로");
        restaurantView.add(ContextCompat.getDrawable(context, R.drawable.seunsal),"순살나라", "호롤로");
        restaurantView.add(ContextCompat.getDrawable(context, R.drawable.tiva),"티바두마리치킨", "호롤로");
        restaurantView.add(ContextCompat.getDrawable(context, R.drawable.kyochon),"교촌치킨", "호롤로");
        restaurantView.add(ContextCompat.getDrawable(context, R.drawable.peli),"페리카나", "호롤로");
        restaurantView.add(ContextCompat.getDrawable(context, R.drawable.bbq),"비비큐", "호롤로");
    }
}
