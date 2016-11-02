package com.jarvis.foodcampus.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.interactor.LoginInteractor;
import com.jarvis.foodcampus.presenter.login.LoginPresenter;
import com.jarvis.foodcampus.presenter.login.LoginPresenterImpl;
import com.jarvis.foodcampus.view.main.MainActivity;
import com.kakao.auth.Session;

/**
 * Created by JunHo on 2016-10-22.
 */

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter loginPresenter;
    private LoginInteractor loginInteractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // db관리 객체
        loginInteractor = new LoginInteractor(this);
        // presenter에서 sharedpreference 사용하려면 context가 있어야 함
        loginPresenter = new LoginPresenterImpl(this, getApplicationContext(), loginInteractor);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
        //Session.getCurrentSession().removeCallback(callback);
    }

    // 여기서 메인으로 쏴주는 메소드
    public void redirectSignupActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

