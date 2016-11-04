package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-10-22.
 */

public class RestaurantModel {
    //private Drawable iconDrawable ;
    private int restaurantId;

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    private int categoryId;
    private String restaurantName;
    private String restaurantInfo;
    private String phone;
    private String openTime;
    private String closeTime;

    //public Drawable getIconDrawable() {

//    return iconDrawable;
//    }

//    public void setIconDrawable(Drawable iconDrawable) {
//        this.iconDrawable = iconDrawable;
//    }

    public RestaurantModel() {

    }
    public RestaurantModel(int restaurntId, int categoryId, String restaurantName,
                           String restaurantInfo, String phone, String openTime,
                           String closeTime) {
        this.restaurantId = restaurntId;
        this.categoryId = categoryId;
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.phone = phone;
        this.openTime = openTime;
        this.closeTime = closeTime;
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

    public String getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(String restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
}
