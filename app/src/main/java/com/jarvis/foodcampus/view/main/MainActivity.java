package com.jarvis.foodcampus.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.presenter.main.MainPresenter;
import com.jarvis.foodcampus.presenter.main.MainPresenterImpl;
import com.jarvis.foodcampus.view.base.BaseActivity;
import com.jarvis.foodcampus.view.detail.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {

    private MainPresenter mainPresenter;

    @BindView(R.id.pizza_btn)
    ImageButton pizzaBtn;
    @BindView(R.id.chicken_btn)
    ImageButton chickenBtn;
    @BindView(R.id.hansik_btn)
    ImageButton hanksikBtn;
    @BindView(R.id.noodle_btn)
    ImageButton noodleBtn;

    @OnClick({R.id.pizza_btn, R.id.chicken_btn, R.id.hansik_btn, R.id.noodle_btn})
    public void onClick(View v) {
        // 혹시 다른 버튼의 기능이 추가될 수도 있으므로
        String btnCheck = mainPresenter.mainBtnCheck(v);

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //타이틀바 제거
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        mainPresenter = new MainPresenterImpl(this, getApplicationContext());

        //pizzaBtn.setOnClickListener(this);
        //chickenBtn.setOnClickListener(this);
    }

    /**
     * 화면에 메세지를 띄워준다
     * MainPresenter에서 메세지 전달
     *
     * @param message
     */
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }
}
