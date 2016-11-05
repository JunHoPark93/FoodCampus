package com.jarvis.foodcampus.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jarvis.foodcampus.model.UserModel;

/**
 * Created by JunHo on 2016-11-01.
 */

public class UserDataSource {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public UserDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
        Log.i("USerDataSourceOpen",databaseHelper.toString());
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    //insert user record

    /**
     * db에 맞게 바꿔야됨
     * @param user
     * @return
     */
    public void addUser(UserModel user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseManage.User.COLUMN_NAME_USER_ID, user.getApi_id());
        values.put(DatabaseManage.User.COLUMN_NAME_USER_NICKNAME, user.getNickName());

        database.insert(DatabaseManage.User.TABLE_NAME, null, values);
        Log.i("sqliteAdd","유저데이터추가되었음");
    }
}
