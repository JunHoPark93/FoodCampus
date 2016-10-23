package com.jarvis.foodcampus.presenter.login;

import com.jarvis.foodcampus.model.UserModel;

/**
 * Created by JunHo on 2016-10-22.
 */

public interface LoginPresenter {
    void onDestroy();

    void saveUserProfile(UserModel userModel);
}
