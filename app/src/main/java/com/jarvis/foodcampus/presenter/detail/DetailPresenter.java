package com.jarvis.foodcampus.presenter.detail;

/**
 * Created by EunBi on 2016-10-23.
 */

public interface DetailPresenter {
    void initData();
    void setRestaurant(String food);
    void getFoodData();
    void setCategory(String category);
    void sendOrderNum();
    void setRadioButton();
    void sendReview(int whichBtn);
    void setReviewData();
    void sendFavorite(String resId);
}
