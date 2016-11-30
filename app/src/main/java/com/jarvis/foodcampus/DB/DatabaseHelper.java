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
    private static final String DATABASE_NAME = "FoodCampus.db";

    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
            DatabaseManage.User.TABLE_NAME + "(" +
            DatabaseManage.User._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            DatabaseManage.User.COLUMN_NAME_USER_ID + " INTEGER, " +
            DatabaseManage.User.COLUMN_NAME_USER_NICKNAME + " TEXT )";


    private static final String SQL_CREATE_RESTAURANT_TABLE = "CREATE TABLE " +
            DatabaseManage.Restaurant.TABLE_NAME + "(" +
            DatabaseManage.Restaurant._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            DatabaseManage.Restaurant.COLUMN_NAME_RESAURANT_ID + " INTEGER, " +
            DatabaseManage.Restaurant.COLUMN_NAME_CATEGORY_ID + " INTEGER, " +
            DatabaseManage.Restaurant.COLUMN_NAME_RESTAURANT_NAME + " TEXT, " +
            DatabaseManage.Restaurant.COLUMN_NAME_RESAURANT_INFO + " TEXT, " +
            DatabaseManage.Restaurant.COLUMN_NAME_PHONE + " TEXT, " +
            DatabaseManage.Restaurant.COLUMN_NAME_OPEN_TIME + " TEXT, " +
            DatabaseManage.Restaurant.COLUMN_NAME_CLOSE_TIME + " TEXT )";

    private static final String SQL_CREATE_FOOD_TABLE = "CREATE TABLE " +
            DatabaseManage.Food.TABLE_NAME + "(" +
            DatabaseManage.Food._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseManage.Food.COLUMN_NAME_FOOD_ID + " INTEGER, " +
            DatabaseManage.Food.COLUMN_NAME_RESTAURANT_ID + " INTEGER, " +
            DatabaseManage.Food.COLUMN_NAME_FOOD_NAME + " TEXT, " +
            DatabaseManage.Food.COLUMN_NAME_FOOD_GROUP + " TEXT, " +
            DatabaseManage.Food.COLUMN_NAME_FOOD_PRICE + " TEXT, " +
            DatabaseManage.Food.COLUMN_NAME_FOOD_INFO + " TEXT )";

    private static final String SQL_CREATE_ORDER_TABLE = "CREATE TABLE " +
            DatabaseManage.Order.TABLE_NAME + "(" +
            DatabaseManage.Order._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseManage.Order.COLUMN_NAME_ORDER_ID + " INTEGER, " +
            DatabaseManage.Order.COLUMN_NAME_USER_ID + " INTEGER, " +
            DatabaseManage.Order.COLUMN_NAME_RESTAURANT_ID + " INTEGER )";

    private static final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " +
            DatabaseManage.Review.TABLE_NAME + "(" +
            DatabaseManage.Review._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseManage.Review.COLUMN_NAME_RESTAURANT_ID + " INTEGER, " +
            DatabaseManage.Review.COLUMN_NAME_USER_ID + " INTEGER, " +
            DatabaseManage.Review.COLUMN_NAME_LIKE_YN + " TEXT )";

    private static final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " +
            DatabaseManage.Favorite.TABLE_NAME + "(" +
            DatabaseManage.Favorite._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DatabaseManage.Favorite.COLUMN_NAME_RESTAURANT_ID + " INTEGER, " +
            DatabaseManage.Favorite.COLUMN_NAME_RESTARANT_NAME + " STRING, " +
            DatabaseManage.Favorite.COLUMN_NAME_CATEGORY_ID + " INTEGER," +
            DatabaseManage.Favorite.COLUMN_NAME_USER_ID + " INTEGER )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("DatabaseHelper init","디비헬퍼생성자로 초기화되었음");
    }

    // 얘는 처음 테이블 생성될 때만 호출되요
    // 그뒤로는 안되요 다시하려면 앱을 지우던지
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_RESTAURANT_TABLE);
        db.execSQL(SQL_CREATE_FOOD_TABLE);
        db.execSQL(SQL_CREATE_ORDER_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);

        Log.i("DatabaseHelper Table","테이블생성");
    }

    // 업그레이드는 골치아파서 안합니다 버전 관리하기 힘듬
    // 어차피 테이블 5개 거의 고정확정이니.. 나중에 추가 기능이 필요하면 릴리즈할때 테이블 추가해서 릴리즈하는걸로
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

       // db.execSQL("DROP TABLE IF EXISTS " + DatabaseManage.User.TABLE_NAME);
       // onCreate(db);

    }
}
