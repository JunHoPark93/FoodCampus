package com.jarvis.foodcampus.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jarvis.foodcampus.model.ReviewModel;

/**
 * Created by JunHo on 2016-11-14.
 */

public class ReviewDataSource {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public ReviewDataSource(Context context) {
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

    public void addReview(ReviewModel model) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManage.Review.COLUMN_NAME_USER_ID, model.getUserId());
        values.put(DatabaseManage.Review.COLUMN_NAME_RESTAURANT_ID, model.getRestaurantId());
        values.put(DatabaseManage.Review.COLUMN_NAME_LIKE_YN, model.getLikeYN());

        database.insert(DatabaseManage.Review.TABLE_NAME, null, values);

        Log.i("sqliteAdd", "리뷰 데이터추가됨");
    }
}
