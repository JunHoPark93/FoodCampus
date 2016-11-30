package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-11-29.
 */

public class FavoriteModel {

    private int user_id;
    private int restaurantId;
    private int categoryId;
    private String restaurantName;

    public FavoriteModel(int user_id, int restaurantId, int categoryId, String restaurantName) {
        this.user_id = user_id;
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.restaurantName = restaurantName;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
