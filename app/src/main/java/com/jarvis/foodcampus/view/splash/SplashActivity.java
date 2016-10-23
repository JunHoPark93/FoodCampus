package com.jarvis.foodcampus.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.view.login.LoginActivity;

/**
 * Created by Yongjun on 2016-10-03.
 */

public class SplashActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 3000);
    }

    private class splashHandler implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(getApplication(),LoginActivity.class));
            finish();
        }
    }
}
