package com.jarvis.foodcampus.presenter.restaurant;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.model.RestaurantModel;
import com.jarvis.foodcampus.view.restaurant.RestaurantView;

/**
 * Created by JunHo on 2016-10-22.
 */

public class RestaurantPresenterImpl implements RestaurantPrestenter {

    private RestaurantView restaurantView;
    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private RestaurantModel[] restaurantModels;
    private int whichRestaurant;

    public RestaurantPresenterImpl(RestaurantView restaurantView, Context context) {
        this.restaurantView = restaurantView;
        this.context = context;

        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    public void whichRestaurant(String restaurant) {
        // 메인에서 어떤 레스토랑이 넘어갔는지 정보를 푼다

        switch (restaurant) {

            case "chicken":
                whichRestaurant = 1;
                break;
            case "pizza":
                whichRestaurant = 2;
                break;
            case "korean":
                whichRestaurant = 3;
                break;
            case "chinese":
                whichRestaurant = 4;
                break;

            case "night":
                whichRestaurant = 5;
                break;

            case "japanese":
                whichRestaurant = 6;
                break;

            case "search":
                whichRestaurant = 7;
                break;

            case "random":
                whichRestaurant = ((int)(Math.random()*6))+1;
                System.out.println("랜덤랜덤픽 : " + whichRestaurant);
                break;

            default:
                break;

        }
    }


    /**
     *  어떤 데이터를 붙일지 sqlite 에서 가져올 것
     */
    public void initData(String searchContent) {

        // 카테코리별로 데이터를 가져온다 (카테고리가 치킨이면 치킨만, 피자면 피자만...)
        Log.i("레스토랑initData",String.valueOf(whichRestaurant));
        //String sql = "SELECT * FROM " + "restaurant " + "where category_id = " + "'"+whichRestaurant+"'";
        //Cursor result = database.rawQuery(sql, null);

        String sql;
        if(whichRestaurant==7){
            System.out.println("Search : "+searchContent);

            sql = "SELECT * " +
                    "FROM restaurant r, food f " +
                    "WHERE restaurant_name like '%" + searchContent + "%' " +
                    "OR (r.restaurant_id = f.restaurant_id " +
                    "AND (f.food_name LIKE '%" + searchContent + "%' " +
                    "OR f.food_group LIKE '%" + searchContent + "%')) " +
                    "GROUP BY  r.restaurant_id " +
                    "ORDER BY r.restaurant_name";
//            sql = "SELECT * FROM restaurant r JOIN (SELECT RESTAURANT_ID FROM food " +
//                    "WHERE FOOD_NAME LIKE " + "'%" + searchContent + "%'" +
//                    "OR FOOD_GROUP LIKE " + "'%" + searchContent + "%' GROUP BY restaurant_id) f ON f.RESTAURANT_ID = r.RESTAURANT_ID";
//            sql= "SELECT * FROM " + "restaurant " + "where restaurant_name like " + "'%" + searchContent + "%'";
        }else {
            sql = "SELECT * FROM " + "restaurant " + "where category_id = " + "'" + whichRestaurant + "' " +
                    "ORDER BY restaurant_name";
        }

        Cursor result = database.rawQuery(sql, null);

        restaurantModels = new RestaurantModel[result.getCount()];
        System.out.println("갯수"+result.getCount());

        try {
            if (result.moveToFirst()) {
                for (int i = 0; i < result.getCount(); i++) {
                    int restaurantId = result.getInt(1);
                    int categoryId = result.getInt(2);
                    String restaurantName = result.getString(3);
                    String restaurantInfo = result.getString(4);
                    String phone = result.getString(5);
                    String openTime = result.getString(6);
                    String closeTime = result.getString(7);

                    restaurantModels[i] = new RestaurantModel(restaurantId, categoryId, restaurantName,
                            restaurantInfo, phone, openTime, closeTime);

                    restaurantView.add(restaurantModels[i]); // 레스토랑 모델 뷰쪽에 던짐
                    result.moveToNext(); // 칼럼한줄내려
                }
            }
        } finally {
            if (result != null && !result.isClosed()) {
                result.close();
            }
        }
        database.close();
    }
}
