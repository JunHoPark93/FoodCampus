package com.jarvis.foodcampus.presenter.detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.model.DetailModel;
import com.jarvis.foodcampus.model.FoodModel;
import com.jarvis.foodcampus.view.detail.DetailView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

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
    private SharedPreferences sharedPreferences;

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

    // 스트링 변수 위로 뺴고 order 테이블도 같이 쿼리문짜줘
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

    public void getOrderData() {
        int ordernum = 0;
        String sql = "SELECT COUNT(*) FROM " + "ordernum " + "WHERE restaurant_id = " + "'" + restaurant + "'";
        Cursor result = database.rawQuery(sql, null);

        if(result.moveToFirst()) {
            ordernum = result.getInt(0);
        }

        detailView.setOrder(ordernum);

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
            getSingleRestaurantData(); // 디테일로 정보 뿌리기

            database = databaseHelper.getReadableDatabase(); // getSingleRestaurantData()안에서 close 해서 다시 open
            getOrderData();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("에러터지는건 등록된 음식이 하나도없어서 그럼");
            Toast.makeText(context, "음식 정보가 하나도 없습니다", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void sendOrderNum() {

        sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        final String api_id = sharedPreferences.getString("serial_number",null);

        class SendOrderNumber extends AsyncTask<String, Void, String> {

            private URL url;

            public SendOrderNumber(String url) throws MalformedURLException {
                this.url=new URL(url);
            }

            private String readStream(InputStream in) throws IOException {
                StringBuilder jsonHtml = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line = null;

                while((line = reader.readLine()) != null)
                    jsonHtml.append(line);

                reader.close();
                return jsonHtml.toString();
            }

            @Override
            protected String doInBackground(String... params) {
                try {

                    String postData = "api_id="+api_id+"&restaurant_id="+restaurant;

                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(10000);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(postData.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();
                    String result = readStream(conn.getInputStream());
                    conn.disconnect();

                    return result;
                }
                catch (Exception e) {
                    Log.i("PHPRequest", "request was failed.");
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                System.out.println("주문전송끝");
                super.onPostExecute(s);
            }
        }

        SendOrderNumber sendOrderNumber = null;

        try {
            sendOrderNumber = new SendOrderNumber("http://117.17.158.237:3389/index.php/mOrder");
            sendOrderNumber.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
