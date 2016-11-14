package com.jarvis.foodcampus.presenter.detail;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.R;
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
    private String restaurant;
    private String category;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private FoodModel[] foodModels;
    private Drawable resIcon;

    private ArrayList<String> arrayGroup = new ArrayList<>();
    private HashMap<String, ArrayList<DetailModel>> arrayChild = new HashMap<>();

    public DetailPresenterImpl(DetailView detailView, Context context) {
        this.detailView = detailView;
        this.context = context;

        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void getFoodData() {

        // 어느 음식점에서 꺼내올지?
        String sql = "SELECT * FROM " + "food " + "WHERE "+"restaurant_id = "+ "'" + restaurant + "'";
        Cursor result = database.rawQuery(sql, null);

        foodModels = new FoodModel[result.getCount()];
        System.out.println("푸드 디비길이"+result.getCount());


        if(result.moveToFirst()) {
            for(int i=0; i<result.getCount(); i++) {
                int foodId = result.getInt(1);
                int restaurantId = result.getInt(2);
                String foodName = result.getString(3);
                String foodPrice = result.getString(4);
                String foodGroup = result.getString(5);
                String foodInfo = result.getString(6);
                System.out.println("foodId"+foodId+"resId"+restaurantId+"foodName"+foodName+"foodGroup"+foodGroup
                        +"가격:"+foodPrice+" 인푀"+foodInfo);
                foodModels[i] = new FoodModel(foodId, restaurantId, foodName, foodPrice, foodGroup, foodInfo);

                result.moveToNext();
            }
        }

        result.close();
        database.close();
    }

    public void getSingleRestaurantData() {
        String sql = "SELECT * FROM " + "restaurant " + "WHERE "+"restaurant_id = "+ "'" + restaurant + "'";
        Cursor result = database.rawQuery(sql, null);

        resIcon = getRestaruantIcon(category);

        if(result.moveToFirst()) {
            String resName = result.getString(3);
            String resHour = result.getString(6) + " ~ "+ result.getString(7);
            String resPhone = result.getString(5);


            System.out.println("선택된카테고리"+category);
            detailView.setDisplay(resName, resHour, resPhone, resIcon);

        }
        result.close();
        database.close();

    }

    private Drawable getRestaruantIcon(String category) {
        switch (category) {
            case "chicken":
                resIcon = context.getResources().getDrawable(R.drawable.chicken_main);
                break;

            case "chinese":
                resIcon = context.getResources().getDrawable(R.drawable.noodles_main);
                break;

            case "korean":
                resIcon = context.getResources().getDrawable(R.drawable.rice_main);
                break;

            case "pizza":
                resIcon = context.getResources().getDrawable(R.drawable.pizza_main);
                break;

            case "5":
                break;

            case "6":
                break;
        }

        return resIcon;
    }


    /**
     *  어떤 데이터를 붙일지 서버에서 가져올 것
     */
    public void initData() {

        database = databaseHelper.getReadableDatabase();

        ArrayList<String> arrayList = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();

        try {
            hashMap.put(foodModels[0].getFoodGroup(), foodModels[0].getFoodGroup());
            arrayList.add(foodModels[0].getFoodGroup());

            for(int i=0; i<foodModels.length; i++) {

                if(hashMap.containsValue(foodModels[i].getFoodGroup()))
                    continue;
                else {
                    hashMap.put(foodModels[i].getFoodGroup(), foodModels[i].getFoodGroup());
                    arrayList.add(foodModels[i].getFoodGroup());
                }

            }

            for(int i=0; i<arrayList.size(); i++) {
                arrayGroup.add(arrayList.get(i)); // 그룹에 음식 인포 저장
            }

            //arrayList.get(i) == foodModels[i].getFoodGroup() -> 둘이 묶어준다

            // 이 arraylist배열은 각 그룹에 해당하는 차일드 리스트배열이다
            ArrayList<DetailModel>[] listArray = new ArrayList[arrayList.size()];

            // listarray와 arraylist의 인덱스는 같다
            for(int i=0; i<arrayList.size(); i++) {
                listArray[i] = new ArrayList<DetailModel>();

                for(int k=0; k<foodModels.length; k++) {

                    if(arrayList.get(i).equals(foodModels[k].getFoodGroup())) {
                        listArray[i].add(new DetailModel(arrayList.get(i),foodModels[k].getFoodName()
                                ,foodModels[k].getFoodInfo(), foodModels[k].getFoodPrice()));
                    }
                }
                arrayChild.put(arrayGroup.get(i), listArray[i]);

            }
            detailView.add(arrayGroup, arrayChild);
            getSingleRestaurantData();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("에러터지는건 등록된 음식이 하나도없어서 그럼");
            Toast.makeText(context, "음식 정보가 하나도 없습니다", Toast.LENGTH_LONG).show();
        }

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
