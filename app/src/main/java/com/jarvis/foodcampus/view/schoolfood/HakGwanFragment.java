package com.jarvis.foodcampus.view.schoolfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.presenter.schoolfood.HakGwanPresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JunHo on 2016-11-15.
 */

public class HakGwanFragment extends Fragment implements HakGwanFragmentView {
    @BindView(R.id.hakgwan_mon)
    TextView hakgwanMon;
    @BindView(R.id.hakgwan_monfood)
    TextView hakgwanMonfood;
    @BindView(R.id.hakgwan_tue)
    TextView hakgwanTue;
    @BindView(R.id.hakgwan_tuefood)
    TextView hakgwanTuefood;
    @BindView(R.id.hakgwan_wed)
    TextView hakgwanWed;
    @BindView(R.id.hakgwan_wedfood)
    TextView hakgwanWedfood;
    @BindView(R.id.hakgwan_thu)
    TextView hakgwanThu;
    @BindView(R.id.hakgwan_thufood)
    TextView hakgwanThufood;
    @BindView(R.id.hakgwan_fri)
    TextView hakgwanFri;
    @BindView(R.id.hakgwan_frifood)
    TextView hakgwanFrifood;

    private HakGwanPresenterImpl hakGwanPresenter;


    public HakGwanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hakgwan_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hakGwanPresenter = new HakGwanPresenterImpl(this, getActivity().getApplicationContext());
        hakGwanPresenter.getSchoolFoodData();
    }

    public void addText(ArrayList list1, ArrayList list2) {
        hakgwanMon.setText(list1.get(0).toString());
        hakgwanMonfood.setText(list2.get(0).toString());
        hakgwanTue.setText(list1.get(1).toString());
        hakgwanTuefood.setText(list2.get(1).toString());
        hakgwanWed.setText(list1.get(2).toString());
        hakgwanWedfood.setText(list2.get(2).toString());
        hakgwanThu.setText(list1.get(3).toString());
        hakgwanThufood.setText(list2.get(3).toString());
        hakgwanFri.setText(list1.get(4).toString());
        hakgwanFrifood.setText(list2.get(4).toString());
    }
}
