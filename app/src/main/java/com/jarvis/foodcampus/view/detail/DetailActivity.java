package com.jarvis.foodcampus.view.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

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

    private DetailAdapter detailAdapter;
    private DetailPresenter detailPresenter;


    @OnClick(R.id.detail_phone_btn)
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(detailPhoneBtn.getText().toString().toLowerCase()));
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        listView = (ExpandableListView) this.findViewById(R.id.detail_expendList);

        //임시로 일케 연결함 intent로 넘겨주세요
        detailName.setText("은비네 두마리 치킨");
        detailGrade.setText("좋아요 : 10 / 싫어요 : 5");
        detailOrder.setText("총 주문수 : 8회");
        detailHour.setText("15:00 ~ 23:00");
        detailIntro.setText("안녕하세요? \nJarvis 팀이 만든 Application\n [ 푸드캠퍼스 ] 입니다. \n 맛잇는거 많이 시켜드세용");

        detailPhoneBtn.setText("tel:010-5520-5333");

        detailAdapter = new DetailAdapter(this);
        listView.setAdapter(detailAdapter);

        detailPresenter = new DetailPresenterImpl(this, getApplicationContext());
        detailPresenter.initData();

        //     setItem();

        //     listView.setAdapter(new DetailAdapter(this, arrayGroup, arrayChild));


    }


    @Override
    public void add(ArrayList<String> arrayGroup, HashMap<String, ArrayList<DetailModel>> arrayChild) {
        detailAdapter.addItem(arrayGroup, arrayChild);
    }
}
