package com.jarvis.foodcampus.presenter.detail;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.model.DetailModel;
import com.jarvis.foodcampus.model.FoodModel;
import com.jarvis.foodcampus.view.detail.DetailView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JunHo on 2016-11-05.
 */

public class DetailPresenterImpl implements DetailPresenter {

    private DetailView detailView;
    private Context context;
    private String whichRestaurant;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private FoodModel[] foodModels;


    private ArrayList<String> arrayGroup = new ArrayList<>();
    private HashMap<String, ArrayList<DetailModel>> arrayChild = new HashMap<>();

    public DetailPresenterImpl(DetailView detailView, Context context) {
        this.detailView = detailView;
        this.context = context;

        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    public void whichFood(String restaurant) {

        whichRestaurant = restaurant;
        System.out.println("레스토랑"+whichRestaurant);
    }

    public void getFoodData() {

        // 어느 음식점에서 꺼내올지?
        String sql = "SELECT * FROM " + "food " + "WHERE "+"restaurant_id = "+ "'" + whichRestaurant + "'";
        Cursor result = database.rawQuery(sql, null);

        foodModels = new FoodModel[result.getCount()];
        System.out.println("푸드 디비길이"+result.getCount());

        if(result.moveToFirst()) {
            for(int i=0; i<result.getCount(); i++) {
                int foodId = result.getInt(1);
                int restaurantId = result.getInt(2);
                String foodName = result.getString(3);
                String foodPrice = result.getString(4);
                String foodInfo = result.getString(5);
                foodModels[i] = new FoodModel(foodId, restaurantId, foodName, foodPrice, foodInfo);

                result.moveToNext();
            }
        }
    }

    public void getSingleRestaurantData() {
        String sql = "SELECT * FROM " + "restaurant " + "WHERE "+"restaurant_id = "+ "'" + whichRestaurant + "'";
        Cursor result = database.rawQuery(sql, null);

        if(result.moveToFirst()) {
            String resName = result.getString(3);
            String resHour = result.getString(6);
            String resPhone = result.getString(5);

            detailView.setDisplay(resName, resHour, resPhone);

        }

    }


    /**
     *  어떤 데이터를 붙일지 서버에서 가져올 것
     */
    public void initData() {

        ArrayList<String> arrayList = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();

        try {
            hashMap.put(foodModels[0].getFoodInfo(), foodModels[0].getFoodInfo());
            arrayList.add(foodModels[0].getFoodInfo());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("에러터지는건 카테고리에 음식점이 하나도없어서 그럼");
            Toast.makeText(context, "음식점 정보가 하나도 없습니다", Toast.LENGTH_LONG).show();
        }

        for(int i=0; i<foodModels.length; i++) {

            if(hashMap.containsValue(foodModels[i].getFoodInfo()))
                continue;
            else
                hashMap.put(foodModels[i].getFoodInfo(), foodModels[i].getFoodInfo());
                arrayList.add(foodModels[i].getFoodInfo());
        }

        for(int i=0; i<arrayList.size(); i++) {
            arrayGroup.add(arrayList.get(i)); // 그룹에 음식 인포 저장
        }

        //arrayList.get(i) == foodModels[i].getFoodInfo() -> 둘이 묶어준다

        // 이 arraylist배열은 각 그룹에 해당하는 리스트배열이다
        ArrayList<DetailModel>[] listArray = new ArrayList[arrayList.size()];

        // listarray와 arraylist의 인덱스는 같다
        for(int i=0; i<arrayList.size(); i++) {
            listArray[i] = new ArrayList<DetailModel>();
            for(int k=0; k<foodModels.length; k++) {
                if(arrayList.get(i) == foodModels[k].getFoodInfo()) {
                    listArray[i].add(new DetailModel(arrayList.get(i),foodModels[k].getFoodName()
                    ,foodModels[i].getFoodName(), Integer.parseInt(foodModels[k].getFoodPrice())));
                }
            }
            arrayChild.put(arrayGroup.get(i), listArray[i]);
        }
        detailView.add(arrayGroup, arrayChild);
        getSingleRestaurantData();

        /*
        디폴트 코드
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
        */

    }

}
