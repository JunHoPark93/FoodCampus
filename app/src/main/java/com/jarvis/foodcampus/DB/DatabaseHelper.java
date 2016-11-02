package com.jarvis.foodcampus.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by JunHo on 2016-11-01.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    //private static final String DATABASE_NAME = "MedicalConferenceManager.db";
    private static final String DATABASE_NAME = "FoodCampus.db";

    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
            DatabaseManage.User.TABLE_NAME + "(" +
            DatabaseManage.User.COLUMN_NAME_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            DatabaseManage.User.COLUMN_NAME_USER_NICKNAME + " TEXT )";

    /*
    private static final String SQL_CREATE_RESTAURANT_TABLE = "CREATE TABLE " +
            DatabaseManage.Restaurant.TABLE_NAME + "(" +
            DatabaseManage.Restaurant._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            DatabaseManage.Restaurant.COLUMN_NAME_ITEM_NAME + " TEXT, " +
            DatabaseManage.Restaurant.COLUMN_NAME_DESCRIPTION + " TEXT , " +
            DatabaseManage.Restaurant.COLUMN_NAME_MINIMUM_BID_AMOUNT + " INTEGER , " +
            DatabaseManage.Restaurant.COLUMN_NAME_TARGET_BID_AMOUNT + " INTEGER, " +
            DatabaseManage.Restaurant.COLUMN_NAME_IS_ITEM_SOLD + " INTEGER )";

    private static final String SQL_CREATE_FOOD_TABLE = "CREATE TABLE " +
            DatabaseManage.Food.TABLE_NAME + "(" +
            DatabaseManage.Food._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseManage.Food.COLUMN_NAME_USER_ID + " INTEGER, " +
            DatabaseManage.Food.COLUMN_NAME_ITEM_ID + " INTEGER, " +
            DatabaseManage.Food.COLUMN_NAME_BID_AMOUNT + " INTEGER, " +
            DatabaseManage.Food.COLUMN_NAME_BID_STATUS + " INTEGER, " +
            DatabaseManage.Food.COLUMN_NAME_BID_TIME + " TEXT )";
            */

    //review
    //order


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("DatabaseHelper init","디비헬퍼생성자로 초기화되었음");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("creating table"+DatabaseManage.User.TABLE_NAME);

        db.execSQL(SQL_CREATE_USERS_TABLE);
        //db.execSQL(SQL_CREATE_RESTAURANT_TABLE);
        //db.execSQL(SQL_CREATE_FOOD_TABLE);
        Log.i("DatabaseHelper Table","테이블생성");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseManage.User.TABLE_NAME);
        onCreate(db);

    }
}
