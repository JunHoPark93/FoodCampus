package com.jarvis.foodcampus.view.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.model.FavoriteModel;
import com.jarvis.foodcampus.presenter.favorite.FavoriteAdapter;
import com.jarvis.foodcampus.presenter.favorite.FavoritePresenter;
import com.jarvis.foodcampus.presenter.favorite.FavoritePresenterImpl;
import com.jarvis.foodcampus.view.base.BaseActivity;
import com.jarvis.foodcampus.view.detail.DetailActivity;

import butterknife.ButterKnife;

/**
 * Created by JunHo on 2016-11-29.
 */

public class FavoriteActivity extends BaseActivity implements FavoriteView, AdapterView.OnItemClickListener {
    private ListView listView;
    private FavoriteAdapter favoriteAdapter;
    private FavoritePresenter favoritePresenter;
    private String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);


        listView = (ListView) findViewById(R.id.favorite_listview);
        favoriteAdapter = new FavoriteAdapter();
        listView.setAdapter(favoriteAdapter);

        favoritePresenter = new FavoritePresenterImpl(this, getApplicationContext());
        favoritePresenter.initData();

        listView.setOnItemClickListener(this);
    }

    @Override
    public void add(FavoriteModel favoriteModel) {
        favoriteAdapter.addItem(favoriteModel);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FavoriteModel favoriteModel = (FavoriteModel) parent.getItemAtPosition(position);
        String category;

        // 나중엔 position 에따라 달라짐
        // 인텐트
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("restaurantId",String.valueOf(favoriteModel.getRestaurantId())); // 넘어갈때 레스토랑id 같이 넘김

        switch (favoriteModel.getCategoryId()) {
            case 1:
                category = "chicken";
                break;
            case 2:
                category = "pizza";
                break;
            case 3:
                category = "korean";
                break;
            case 4:
                category = "chinese";
                break;
            case 5:
                category = "night";
                break;
            case 6:
                category = "japanese";
                break;
            default:
                category = "chicken";
        }

        intent.putExtra("categoryId", category); // 디테일액티비티에서 그림아이콘 설정하기 위해서 넘김
        startActivity(intent);
    }


}
