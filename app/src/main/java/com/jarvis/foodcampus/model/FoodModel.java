package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-11-05.
 */

public class FoodModel {

    private int foodId;
    private int restaurantId;
    private String foodName;
    private String foodPrice;
    private String foodGroup;
    private String foodInfo;

    public FoodModel(int foodId, int restaurantId, String foodName, String foodPrice, String foodGroup, String foodInfo) {
        this.foodId = foodId;
        this.restaurantId = restaurantId;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodGroup = foodGroup;
        this.foodInfo = foodInfo;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public String getFoodInfo() {
        return foodInfo;
    }

    public void setFoodInfo(String foodInfo) {
        this.foodInfo = foodInfo;
    }
}
