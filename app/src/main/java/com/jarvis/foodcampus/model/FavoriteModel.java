package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-11-29.
 */

public class FavoriteModel {

    private int user_id;
    private int restaurantId;

    public FavoriteModel(int user_id, int restaurantId) {
        this.user_id = user_id;
        this.restaurantId = restaurantId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
