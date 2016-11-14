package com.jarvis.foodcampus.model;

/**
 * Created by EunBi on 2016-10-23.
 */

public class DetailModel {
    
    private String foodname;
    private String group;
    private String price;
    private String size;


    public DetailModel(String group, String foodname,  String size, String price) {
        this.foodname = foodname;
        this.group = group;
        this.price = price;
        if(size.equals("EMPTY")) {
            this.size = "";
        } else {
            this.size = size;
        }
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
