package com.jarvis.foodcampus.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jarvis.foodcampus.model.OrderModel;

/**
 * Created by JunHo on 2016-11-14.
 */

public class OrderDataSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public OrderDataSource(Context context) {
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

    public void addOrder(OrderModel model) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManage.Order.COLUMN_NAME_ORDER_ID, model.getOrderId());
        values.put(DatabaseManage.Order.COLUMN_NAME_USER_ID, model.getUserId());
        values.put(DatabaseManage.Order.COLUMN_NAME_RESTAURANT_ID, model.getRestaurantId());

        database.insert(DatabaseManage.Order.TABLE_NAME, null, values);

        Log.i("sqliteAdd", "주문 데이터추가됨");
    }
}
