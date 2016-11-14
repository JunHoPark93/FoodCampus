package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-11-14.
 */

public class OrderModel {
    private int orderId;
    private int userId;
    private int restaurantId;

    public OrderModel(int orderId, int userId, int restaurantId) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
}
