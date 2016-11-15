package com.jarvis.foodcampus.view.schoolfood;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.presenter.schoolfood.MyongJinDangPresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JunHo on 2016-11-15.
 */

public class MyongJinDangFragment extends Fragment implements MyonJinDangFragmentView {
    @BindView(R.id.myongjin_mon)
    TextView myongjinMon;
    @BindView(R.id.myongjin_monfood)
    TextView myongjinMonfood;
    @BindView(R.id.myongjin_tue)
    TextView myongjinTue;
    @BindView(R.id.myongjin_tuefood)
    TextView myongjinTuefood;
    @BindView(R.id.myongjin_wed)
    TextView myongjinWed;
    @BindView(R.id.myongjin_wedfood)
    TextView myongjinWedfood;
    @BindView(R.id.myongjin_thu)
    TextView myongjinThu;
    @BindView(R.id.myongjin_thufood)
    TextView myongjinThufood;
    @BindView(R.id.myongjin_fri)
    TextView myongjinFri;
    @BindView(R.id.myongjin_frifood)
    TextView myongjinFrifood;

    private MyongJinDangPresenterImpl myongJinDangPresenter;

    public MyongJinDangFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myongjindang_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myongJinDangPresenter = new MyongJinDangPresenterImpl(this, getActivity().getApplicationContext());
        myongJinDangPresenter.getSchoolFoodData();
    }

    @Override
    public void addText(ArrayList list1, ArrayList list2) {
        myongjinMon.setText(list1.get(0).toString());
        myongjinMonfood.setText(list2.get(0).toString());
        myongjinTue.setText(list1.get(1).toString());
        myongjinTuefood.setText(list2.get(1).toString());
        myongjinWed.setText(list1.get(2).toString());
        myongjinWedfood.setText(list2.get(2).toString());
        myongjinThu.setText(list1.get(3).toString());
        myongjinThufood.setText(list2.get(3).toString());
        myongjinFri.setText(list1.get(4).toString());
        myongjinFrifood.setText(list2.get(4).toString());
    }
}
