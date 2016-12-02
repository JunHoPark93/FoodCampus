package com.jarvis.foodcampus.view.detail;

import android.graphics.drawable.Drawable;

import com.jarvis.foodcampus.model.DetailModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by EunBi on 2016-10-23.
 */

public interface DetailView {

    void add(ArrayList<String> arrayGroup, HashMap<String, ArrayList<DetailModel>> arrayChild);
    void setDisplay(String name, String hour, String phone, Drawable resIcon);
    void setOrder(int num);
    void setDefaultRadioButton();
    void setReview(int y, int n);
    void setFavorite();
}
