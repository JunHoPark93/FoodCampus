package com.jarvis.foodcampus.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jarvis.foodcampus.model.FavoriteModel;

/**
 * Created by JunHo on 2016-11-29.
 */

public class FavoriteDataSource {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FavoriteDataSource(Context context) {
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

    public void addFavorite(FavoriteModel model) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManage.Favorite.COLUMN_NAME_USER_ID, model.getUser_id());
        values.put(DatabaseManage.Favorite.COLUMN_NAME_RESTAURANT_ID, model.getRestaurantId());

        database.insert(DatabaseManage.Favorite.TABLE_NAME, null, values);

        Log.i("sqliteAdd", "즐겨찾기 데이터추가됨");
    }
}
