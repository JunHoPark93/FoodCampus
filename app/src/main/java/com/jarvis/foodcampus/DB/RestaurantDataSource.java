package com.jarvis.foodcampus.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JunHo on 2016-11-01.
 */

public class RestaurantDataSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public RestaurantDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

}
