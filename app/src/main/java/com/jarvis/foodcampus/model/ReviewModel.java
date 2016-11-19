package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-11-14.
 */

public class ReviewModel {
    private int userId;
    private int restaurantId;
    private String likeYN;

    public ReviewModel(int userId, int restaurantId, String likeYN) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.likeYN = likeYN;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getLikeYN() {
        return likeYN;
    }

    public void setLikeYN(String likeYN) {
        this.likeYN = likeYN;
    }
}
