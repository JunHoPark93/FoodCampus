package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-11-14.
 */

public class ReviewModel {
    private int userId;
    private int restaurantId;
    private int likeYN;


    public ReviewModel(int userId, int restaurantId, int likeYN) {
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

    public int getLikeYN() {
        return likeYN;
    }

    public void setLikeYN(int likeYN) {
        this.likeYN = likeYN;
    }
}
