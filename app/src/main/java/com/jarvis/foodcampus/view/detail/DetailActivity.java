package com.jarvis.foodcampus.view.detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.model.DetailModel;
import com.jarvis.foodcampus.presenter.detail.DetailAdapter;
import com.jarvis.foodcampus.presenter.detail.DetailPresenter;
import com.jarvis.foodcampus.presenter.detail.DetailPresenterImpl;
import com.jarvis.foodcampus.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by EunBi on 2016-10-23.
 */

public class DetailActivity extends BaseActivity implements DetailView {

    ExpandableListView listView;
    @BindView(R.id.detail_phone_btn)
    Button detailPhoneBtn;
    @BindView(R.id.detail_grade)
    TextView detailGrade;
    @BindView(R.id.detail_name)
    TextView detailName;
    @BindView(R.id.detail_order)
    TextView detailOrder;
    @BindView(R.id.detail_hour)
    TextView detailHour;
    @BindView(R.id.detail_intro)
    TextView detailIntro;
    @BindView(R.id.detail_review_btn)
    Button reviewBtn;
    @BindView(R.id.detail_radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.detailImageView)
    ImageView detailImageView;
    @BindView(R.id.detail_neutral_btn)
    RadioButton detailNeutralBtn;
    @BindView(R.id.detail_like_btn)
    RadioButton detailLikeBtn;
    @BindView(R.id.detail_hate_btn)
    RadioButton detailHateBtn;
    @BindView(R.id.detail_progressbar)
    ProgressBar detailProgressbar;

    private DetailAdapter detailAdapter;
    private DetailPresenter detailPresenter;

    @OnClick(R.id.detail_phone_btn)
    public void onClick(View v) {
        // 서버 요청해야됨
        detailPresenter.sendOrderNum();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(detailPhoneBtn.getText().toString().toLowerCase()));
        startActivity(intent);
    }

    @OnClick({R.id.detail_neutral_btn, R.id.detail_like_btn, R.id.detail_hate_btn})
    public void onRadioButtonClick(View v) {
        Toast.makeText(this, "누가눌렸나" + detailNeutralBtn.isChecked() + detailLikeBtn.isChecked() + detailHateBtn.isChecked(), Toast.LENGTH_SHORT).show();
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        /**
         *  인텐트 받는부분인데 뷰에서 하면 안되는 기능인데 어떻게 빼지
         *  /////////////////////////////////////////////////
         */
        String res = getIntent().getStringExtra("restaurantId");
        String category = getIntent().getStringExtra("categoryId");
        /**
         *  여기까지
         *  /////////////////////////////////////////
         */

        listView = (ExpandableListView) this.findViewById(R.id.detail_expendList);

        detailAdapter = new DetailAdapter(this);
        listView.setAdapter(detailAdapter);

        detailPresenter = new DetailPresenterImpl(this, getApplicationContext());
        detailPresenter.setRestaurant(res);
        detailPresenter.setCategory(category);
        detailPresenter.getFoodData();
        detailPresenter.initData();
        detailPresenter.setRadioButton();
        detailPresenter.setReviewData();

        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.setVisibility(View.VISIBLE);
                reviewBtn.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public void add(ArrayList<String> arrayGroup, HashMap<String, ArrayList<DetailModel>> arrayChild) {
        detailAdapter.addItem(arrayGroup, arrayChild);
    }

    /**
     * 뷰 (레이아웃) 설정 메소드 설정하고 프레젠터에서 넘겨받고 여기서 단순히 세팅만 해줄거임
     */

    public void setDisplay(String name, String hour, String phone, Drawable resIcon) {
        detailName.setText(name);
        detailHour.setText(hour);
        detailPhoneBtn.setText("tel:" + phone);
        detailImageView.setImageDrawable(resIcon);
    }

    public void setOrder(int num) {
        detailOrder.setText("전체 " + num + " 회 주문함");
    }

    public void setDefaultRadioButton() {
        detailNeutralBtn.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "빽프레스", Toast.LENGTH_SHORT).show();
        int whichBtn = 1;

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            // 체크안되는 에러
        } else if (detailNeutralBtn.isChecked()) {
            whichBtn = 1; // 중립처리 해야됨
        } else if (detailLikeBtn.isChecked()) {
            whichBtn = 2;
            detailPresenter.sendReview(whichBtn);
        } else if (detailHateBtn.isChecked()) {
            whichBtn = 3;
            detailPresenter.sendReview(whichBtn);
        }

        finish();

        return;
    }

    @Override
    public void setReview(int Y, int N) {
        detailProgressbar.setMax(Y + N);
        detailProgressbar.setProgress(N);
    }
}
