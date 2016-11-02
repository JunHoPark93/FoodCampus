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
        static final String COLUMN_NAME_ITEM_NAME = "item_name";
        static final String COLUMN_NAME_DESCRIPTION = "description";
        static final String COLUMN_NAME_MINIMUM_BID_AMOUNT = "minimum_bid_amount";
        static final String COLUMN_NAME_TARGET_BID_AMOUNT = "target_bid_amount";
        static final String COLUMN_NAME_IS_ITEM_SOLD = "is_item_sold";
    }

    static class Food implements BaseColumns {
        static final String TABLE_NAME = "food";
        static final String COLUMN_NAME_USER_ID = "user_id";
        static final String COLUMN_NAME_ITEM_ID = "item_id";
        static final String COLUMN_NAME_BID_AMOUNT = "bid_amount";
        static final String COLUMN_NAME_BID_TIME = "bid_time";
        static final String COLUMN_NAME_BID_STATUS = "bid_status";
    }



}
