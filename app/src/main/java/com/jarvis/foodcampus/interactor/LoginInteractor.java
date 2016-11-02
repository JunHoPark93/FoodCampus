package com.jarvis.foodcampus.interactor;

import android.content.Context;
import android.util.Log;

import com.jarvis.foodcampus.DB.FoodDataSource;
import com.jarvis.foodcampus.DB.RestaurantDataSource;
import com.jarvis.foodcampus.DB.UserDataSource;
import com.jarvis.foodcampus.model.UserModel;

/**
 * Created by JunHo on 2016-11-01.
 */

public class LoginInteractor {

    private UserDataSource userDataSource;
    private RestaurantDataSource restaurantDataSource;
    private FoodDataSource foodDataSource;

    public LoginInteractor(Context context) {

        userDataSource = new UserDataSource(context);
        Log.i("LoginInteractor Cons","로그인인터렉터생성자");
    }

    public void login(long userName, String nickName) {
        // db열어
        userDataSource.open();

        UserModel userModel = new UserModel(userName, nickName);

        userDataSource.addUser(userModel);
    }

}
