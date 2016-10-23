package com.jarvis.foodcampus.model;

/**
 * Created by EunBi on 2016-10-23.
 */

public class DetailModel {
    
    private String foodname;
    private String group;
    private int price;
    private String size;
  
    
    public DetailModel(String group, String food, String size, int price){
        this.foodname = food;
        this.group = group;
        this.price = price;
        this.size = size;
    }
    
    public DetailModel(String food, String size, int price){

        this.foodname = food;
        this.group = "그룹";
        this.price = price;
        this.size = size;
    }
    public String getFood() {
        return foodname;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setFood(String food) {
        this.foodname = food;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String menu) {
        this.group = group;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    
}
