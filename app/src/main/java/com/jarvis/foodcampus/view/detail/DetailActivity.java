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

/**
 * Created by EunBi on 2016-10-23.
 */

public class DetailActivity  extends BaseActivity implements DetailView {
    ExpandableListView listView;



    private TextView _grade, _name, _orderNumber, _openHour, _intro;
    private Button _phoneBtn;


    private DetailAdapter detailAdapter;
    private DetailPresenter detailPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        listView = (ExpandableListView) this.findViewById(R.id.detail_expendList);

        _grade = (TextView)this.findViewById(R.id.detail_grade);
        _name = (TextView)this.findViewById(R.id.detail_name);
        _openHour = (TextView)this.findViewById(R.id.detail_hour);
        _orderNumber = (TextView)this.findViewById(R.id.detail_order);
        _intro = (TextView)this.findViewById(R.id.detail_intro);

        _phoneBtn = (Button)this.findViewById(R.id.detail_phone_btn);



        //임시로 일케 연결함 intent로 넘겨주세요
        _name.setText("은비네 두마리 치킨");
        _grade.setText("좋아요 : 10 / 싫어요 : 5");
        _orderNumber.setText("총 주문수 : 8회");
        _openHour.setText("15:00 ~ 23:00");
        _intro.setText("안녕하세요? \nJarvis 팀이 만든 Application\n [ 푸드캠퍼스 ] 입니다. \n 맛잇는거 많이 시켜드세용");

        _phoneBtn.setText("tel:010-5520-5333");


        detailAdapter = new DetailAdapter(this.getApplicationContext());
        listView.setAdapter(detailAdapter);

        detailPresenter = new DetailPresenterImpl(this, getApplicationContext());
        detailPresenter.initData();

   //     setItem();

   //     listView.setAdapter(new DetailAdapter(this, arrayGroup, arrayChild));

        _phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(_phoneBtn.getText().toString().toLowerCase()));
                startActivity(intent);
            }
        });

    }


    @Override
    public void add(ArrayList<String> arrayGroup,HashMap<String, ArrayList<DetailModel>> arrayChild) {
        detailAdapter.addItem(arrayGroup, arrayChild);
    }
}
