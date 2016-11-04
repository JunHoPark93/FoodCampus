package com.jarvis.foodcampus.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jarvis.foodcampus.model.RestaurantModel;

/**
 * Created by JunHo on 2016-11-01.
 */

public class RestaurantDataSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public RestaurantDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
        Log.i("Restaurant Open",databaseHelper.toString());


    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public void addRestaurant(RestaurantModel model) {

        ContentValues values = new ContentValues();
        values.put(DatabaseManage.Restaurant.COLUMN_NAME_RESAURANT_ID, model.getRestaurantId());
        values.put(DatabaseManage.Restaurant.COLUMN_NAME_CATEGORY_ID, model.getCategoryId());
        values.put(DatabaseManage.Restaurant.COLUMN_NAME_RESTAURANT_NAME, model.getRestaurantName());
        values.put(DatabaseManage.Restaurant.COLUMN_NAME_RESAURANT_INFO, model.getRestaurantInfo());
        values.put(DatabaseManage.Restaurant.COLUMN_NAME_PHONE, model.getPhone());
        values.put(DatabaseManage.Restaurant.COLUMN_NAME_OPEN_TIME, model.getOpenTime());
        values.put(DatabaseManage.Restaurant.COLUMN_NAME_CLOSE_TIME, model.getCloseTime());

        //System.out.println(model.getRestaurantName()+"레스토랑이름");

        database.insert(DatabaseManage.Restaurant.TABLE_NAME, null, values);

        //values.put(DatabaseManage.User.COLUMN_NAME_PASSWORD, user.getPassword());
        //values.put(DatabaseManage.User.COLUMN_NAME_CREATED_TIME, user.getCreatedAt());
        //long userId = mDatabase.insert(AuctionContract.User.TABLE_NAME, null, values);
        //return userId;
        Log.i("sqliteAdd","레스토랑데이터추가되었음");
    }
}
