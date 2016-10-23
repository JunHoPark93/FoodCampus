package com.jarvis.foodcampus.presenter.login;

import android.content.Context;
import android.content.SharedPreferences;

import com.jarvis.foodcampus.model.UserModel;
import com.jarvis.foodcampus.view.login.LoginView;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JunHo on 2016-10-21.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private SessionCallback callback;
    private Context context;
    private UserModel userModel;
    private SharedPreferences sharedPreferences;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = context;

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
    }

    @Override
    public void onDestroy() {
        Session.getCurrentSession().removeCallback(callback);
        loginView = null;
        System.out.println("onDestroy called!!");
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null) {
                Logger.e(exception);
            }
        }
    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {

            }

            //로그인 한번 성공하면 세션에 남아있어서 로그인 화면 지나서 바로 메인으로 넘어가요
            @Override
            public void onSuccess(UserProfile result) {
                System.out.println("UserProfile::::::::::::::::" + result);
                System.out.println("UserID::::::::::::::::" + result.getId());
                System.out.println("UserNickname::::::::::::::::" + result.getNickname());

                userModel = new UserModel(result.getId(), result.getNickname());
                saveUserProfile(userModel); // 유저모델에 저장

                loginView.redirectSignupActivity(); // 메인화면으로 쏨

            }
        });
    }

    //사용자정보 저장

    /**
     *  public 으로 빼야되나?
     * @param userModel
     */
    @Override
    public void saveUserProfile(UserModel userModel) {
        sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

/*
        serial_number는 카카오에서 반환해주는 user_id라고 보면됨 이것도 고유값이긴 한데
        DB에 auto increment 설정해서 1부터 자체적으로 user_id 생성할거
*/
        editor.putString("serial_number", String.valueOf(userModel.getId()));
        editor.putString("nickname", userModel.getNickName());
        editor.commit();
    }

}
