package com.jarvis.foodcampus.presenter.detail;

import android.content.Context;

import com.jarvis.foodcampus.model.DetailModel;
import com.jarvis.foodcampus.view.detail.DetailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by EunBi on 2016-10-23.
 */

public class DetailPresenterImpl implements DetailPresenter {
    private DetailView detailView;
    private Context context;


    private ArrayList<String> arrayGroup = new ArrayList<>();
    private HashMap<String, ArrayList<DetailModel>> arrayChild = new HashMap<>();

    public DetailPresenterImpl(DetailView detailView, Context context) {
        this.detailView = detailView;
        this.context = context;
    }

    /**
     *  어떤 데이터를 붙일지 서버에서 가져올 것
     */
    public void initData() {
        arrayGroup.add("Pizza");
        arrayGroup.add("Chicken");
        arrayGroup.add("Pork");


        ArrayList<DetailModel> arrayPizza = new ArrayList<DetailModel>();
        ArrayList<DetailModel> arrayChicken = new ArrayList<DetailModel>();
        ArrayList<DetailModel> arrayPork = new ArrayList<DetailModel>();

        arrayPizza.add(new DetailModel(arrayGroup.get(0),"콤비네이션","L",13000));
        arrayPizza.add(new DetailModel(arrayGroup.get(0),"불고기","M",14000));
        arrayPizza.add(new DetailModel(arrayGroup.get(0),"고구마","XL",15000));
        arrayChicken.add(new DetailModel(arrayGroup.get(1),"순살","1마리",15000));
        arrayChicken.add(new DetailModel(arrayGroup.get(1),"후라이드","1마리",16000));
        arrayChicken.add(new DetailModel(arrayGroup.get(1),"양념","1마리",17000));
        arrayPork.add(new DetailModel(arrayGroup.get(2),"일반","XL",15000));
        arrayPork.add(new DetailModel(arrayGroup.get(2),"양념","XL",15000));
        arrayPork.add(new DetailModel(arrayGroup.get(2),"직화","XL",15000));


        arrayChild.put(arrayGroup.get(0),arrayPizza);
        arrayChild.put(arrayGroup.get(1),arrayChicken);
        arrayChild.put(arrayGroup.get(2),arrayPork);

        detailView.add(arrayGroup, arrayChild);

    }

}
