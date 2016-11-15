package com.jarvis.foodcampus.model;

/**
 * Created by JunHo on 2016-11-06.
 */

public class SchoolFoodModel {
    private String date;
    private String food;

    public SchoolFoodModel(String food, String date) {

        this.date = date;  
        this.food = food;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

}
