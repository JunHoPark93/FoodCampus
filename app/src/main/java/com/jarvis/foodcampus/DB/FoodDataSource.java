package com.jarvis.foodcampus.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jarvis.foodcampus.model.FoodModel;

/**
 * Created by JunHo on 2016-11-01.
 */

public class FoodDataSource {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FoodDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public void addFood(FoodModel model) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManage.Food.COLUMN_NAME_FOOD_ID, model.getFoodId());
        values.put(DatabaseManage.Food.COLUMN_NAME_RESTAURANT_ID, model.getRestaurantId());
        values.put(DatabaseManage.Food.COLUMN_NAME_FOOD_NAME, model.getFoodName());
        values.put(DatabaseManage.Food.COLUMN_NAME_FOOD_PRICE, model.getFoodPrice());
        values.put(DatabaseManage.Food.COLUMN_NAME_FOOD_INFO, model.getFoodInfo());

        database.insert(DatabaseManage.Food.TABLE_NAME, null, values);

        Log.i("sqliteAdd", "음식데이터추가됨");
    }
}
