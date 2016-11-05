package com.jarvis.foodcampus.DB;

import android.provider.BaseColumns;

/**
 * Created by JunHo on 2016-11-01.
 */

public class DatabaseManage {

    public DatabaseManage() {

    }

    /**
     *  테이블 레코드대로 맞춰야됭
     */
    static class User implements BaseColumns {
        static final String TABLE_NAME = "users";
        static final String COLUMN_NAME_USER_ID = "user_id";
        static final String COLUMN_NAME_USER_NICKNAME = "usernickname";
    }


    static class Restaurant implements BaseColumns {
        static final String TABLE_NAME = "restaurant";
        static final String COLUMN_NAME_RESAURANT_ID = "restaurant_id";
        static final String COLUMN_NAME_CATEGORY_ID = "category_id";
        static final String COLUMN_NAME_RESTAURANT_NAME = "restaurant_name";
        static final String COLUMN_NAME_RESAURANT_INFO = "restaurant_info";
        static final String COLUMN_NAME_PHONE = "phone";
        static final String COLUMN_NAME_OPEN_TIME = "open_time";
        static final String COLUMN_NAME_CLOSE_TIME = "close_time";
    }

    static class Food implements BaseColumns {
        static final String TABLE_NAME = "food";
        static final String COLUMN_NAME_FOOD_ID = "user_id";
        static final String COLUMN_NAME_RESTAURANT_ID = "restaurant_id";
        static final String COLUMN_NAME_FOOD_NAME = "food_name";
        static final String COLUMN_NAME_FOOD_PRICE = "food_price";
        static final String COLUMN_NAME_FOOD_INFO = "food_info";
    }
}
