package com.jarvis.foodcampus.presenter.restaurant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.model.RestaurantModel;
import com.jarvis.foodcampus.view.restaurant.RestaurantView;

/**
 * Created by JunHo on 2016-10-22.
 */

public class RestaurantPresenterImpl implements RestaurantPrestenter {

    private RestaurantView restaurantView;
    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private RestaurantModel[] restaurantModels;

    public RestaurantPresenterImpl(RestaurantView restaurantView, Context context) {
        this.restaurantView = restaurantView;
        this.context = context;

        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    /**
     *  어떤 데이터를 붙일지 sqlite 에서 가져올 것
     */
    public void initData() {

        String sql = "SELECT * FROM " + "restaurant";
        Cursor result = database.rawQuery(sql, null);

        restaurantModels = new RestaurantModel[result.getCount()];
        System.out.println("갯수"+result.getCount());

        if(result.moveToFirst()) {
            for (int i = 0; i < result.getCount(); i++) {
                int restaurantId = result.getInt(0);
                int categoryId = result.getInt(1);
                String restaurantName = result.getString(2);
                String restaurantInfo = result.getString(3);
                String phone = result.getString(4);
                String openTime = result.getString(5);
                String closeTime = result.getString(6);

                restaurantModels[i] = new RestaurantModel(restaurantId, categoryId, restaurantName,
                        restaurantInfo, phone, openTime, closeTime);

                restaurantView.add(restaurantModels[i]); // 레스토랑 모델 뷰쪽에 던짐
                result.moveToNext(); // 칼럼한줄내려
            }
        }
    }
}
