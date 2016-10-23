package com.jarvis.foodcampus.model;

import android.graphics.drawable.Drawable;

/**
 * Created by JunHo on 2016-10-22.
 */

public class RestaurantModel {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String extra ;

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
