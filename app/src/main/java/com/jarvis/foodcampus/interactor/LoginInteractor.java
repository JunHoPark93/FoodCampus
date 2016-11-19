package com.jarvis.foodcampus.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.DB.FoodDataSource;
import com.jarvis.foodcampus.DB.OrderDataSource;
import com.jarvis.foodcampus.DB.RestaurantDataSource;
import com.jarvis.foodcampus.DB.ReviewDataSource;
import com.jarvis.foodcampus.DB.UserDataSource;
import com.jarvis.foodcampus.model.FoodModel;
import com.jarvis.foodcampus.model.OrderModel;
import com.jarvis.foodcampus.model.RestaurantModel;
import com.jarvis.foodcampus.model.ReviewModel;
import com.jarvis.foodcampus.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JunHo on 2016-11-01.
 */

public class LoginInteractor {

    private UserDataSource userDataSource;
    private RestaurantDataSource restaurantDataSource;
    private FoodDataSource foodDataSource;
    private OrderDataSource orderDataSource;
    private ReviewDataSource reviewDataSource;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Context context;
    private UserModel userModel;
    private LoginRequest loginRequest;

    private RestaurantModel[] restaurantModels; // 서버에서 받아온거 내장에 박기위한 모델
    private FoodModel[] foodModels; // 위와 같음
    private OrderModel[] orderModels;
    private ReviewModel[] reviewModels;

    public LoginInteractor(Context context) {

        userDataSource = new UserDataSource(context);
        restaurantDataSource = new RestaurantDataSource(context);
        foodDataSource = new FoodDataSource(context);
        orderDataSource = new OrderDataSource(context);
        reviewDataSource = new ReviewDataSource(context);

        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    //    회원정보를 서버로 요청한다.
    public void connectLogin(UserModel userModel) throws MalformedURLException {
        this.userModel = userModel; // 유저모델을 받아왔으나 id값을 바꿔야됨
        try {
            loginRequest = new LoginRequest("http://117.17.158.237:3389/index.php/mLogin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginRequest.execute("" + userModel.getApi_id(), userModel.getNickName());
    }

    private class LoginRequest extends AsyncTask<String, Void, String> {
        private URL url;

        public LoginRequest(String url) throws MalformedURLException {
            this.url = new URL(url);
        }

        private String readStream(InputStream in) throws IOException {
            StringBuilder jsonHtml = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = null;

            while ((line = reader.readLine()) != null)
                jsonHtml.append(line);

            reader.close();
            return jsonHtml.toString();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String postData = "api_id=" + params[0] + "&user_name=" + params[1];
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("true")) {
//                DB INSERT SUCCESS
//                첫 로그인 했을때 자동회원가입
                Toast.makeText(context, "Welcome to Food Campus !", Toast.LENGTH_LONG).show();
            } else if (s.equals("false")) {
//                DB INSERT ERROR
            } else {
//                이미 가입되어있는 회원
                try {
                    JSONObject jObject = new JSONObject(s);
                    JSONArray results = jObject.getJSONArray("result");
                    JSONArray restaurants = jObject.getJSONArray("restaurants");
                    JSONArray foods = jObject.getJSONArray("foods");
                    JSONArray reviews = jObject.getJSONArray("reviews");
                    JSONArray order = jObject.getJSONArray("orders");

                    System.out.println("주문제이슨 "+order);
                    System.out.println("리뷰제이슨" +reviews);

                    System.out.println(restaurants);
                    System.out.println("음식데이터 제이슨배열"+foods);

                    JSONObject temp = results.getJSONObject(0);
                    System.out.println("============================================");
                    System.out.println(temp.getString("user_id"));
                    System.out.println("============================================");
                    userModel.setUser_id(temp.getInt("user_id"));

                    SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("user_id", String.valueOf(temp.getInt("user_id")));
                    editor.commit();

                    /**
                     *  login먼저 호출 (유저 아이디, 닉네임)
                     *  이거 먼저 호출하면 디비 날리고 초기화
                     */
                    //////////////////////////////////////////////////////// 레스토랑시작

                    restaurantModels = new RestaurantModel[restaurants.length()]; // 레스토랑 모델 json옵젝 갯수만큼 초기화

                    System.out.println("레스토랑모델갯수"+restaurants.length());

                    for(int i=0; i<restaurants.length(); i++) {
                        JSONObject resTemp = restaurants.getJSONObject(i);
                        System.out.println("제이슨"+resTemp.toString());
                        String resId = resTemp.getString("RESTAURANT_ID");
                        String resCategory = resTemp.getString("CATEGORY_ID");
                        String resName = resTemp.getString("RESTAURANT_NAME");
                        String resInfo = resTemp.getString("RESTAURANT_INFO");
                        String resPhone = resTemp.getString("PHONE");
                        String resOpenTime = resTemp.getString("OPEN_TIME");
                        String resCloseTime = resTemp.getString("CLOSE_TIME");

                        System.out.println("파싱"+resId+resCategory+resName);
                        // 갯수만큼 레스토랑 모델 초기화
                        restaurantModels[i] = new RestaurantModel(Integer.parseInt(resId), Integer.parseInt(resCategory),
                                resName, resInfo, resPhone, resOpenTime, resCloseTime);

                        System.out.println("잘들어"+restaurantModels[i].getRestaurantName());

                        /////////////////////////////////////////////////////////////////  레스토랑끝
                    }


                    /////////////////////////////////////////////////////////////////  푸드 시작
                    foodModels = new FoodModel[foods.length()];

                    for(int i=0; i<foods.length(); i++) {
                        JSONObject foodTemp = foods.getJSONObject(i);
                        System.out.println("푸드제이슨"+foodTemp.toString());

                        String foodId = foodTemp.getString("FOOD_ID");
                        String resId = foodTemp.getString("RESTAURANT_ID");
                        String foodName = foodTemp.getString("FOOD_NAME");
                        String foodGroup = foodTemp.getString("FOOD_GROUP");
                        String foodPrice = foodTemp.getString("FOOD_PRICE");
                        String foodInfo = foodTemp.getString("FOOD_INFO");

                        foodModels[i] = new FoodModel(Integer.parseInt(foodId), Integer.parseInt(resId),
                                foodName, foodGroup, foodPrice, foodInfo);
                    }

                    /////////////////////////////////////////////////////////////////  푸드 끝


                    ////////////////////////////////////////////////////////////////  주문 시작

                    orderModels = new OrderModel[order.length()];

                    for(int i=0; i<order.length(); i++) {
                        JSONObject orderTemp = order.getJSONObject(i);

                        String orderId = orderTemp.getString("ORDER_ID");
                        String userId = orderTemp.getString("USER_ID");
                        String restaurantId = orderTemp.getString("RESTAURANT_ID");

                        orderModels[i] = new OrderModel(Integer.parseInt(orderId), Integer.parseInt(userId), Integer.parseInt(restaurantId));
                    }



                    ////////////////////////////////////////////////////////////////  주문 끝





                    ////////////////////////////////////////////////////////////////  리뷰 시작

                    reviewModels = new ReviewModel[reviews.length()];

                    for(int i=0; i<reviews.length(); i++) {
                        JSONObject reviewTemp = reviews.getJSONObject(i);

                        String userID = reviewTemp.getString("USER_ID");
                        String likeYN = reviewTemp.getString("LIKE_YN");
                        String restaurantId = reviewTemp.getString("RESTAURANT_ID");

                        reviewModels[i] = new ReviewModel(Integer.parseInt(userID), Integer.parseInt(restaurantId), likeYN);
                    }
                    ////////////////////////////////////////////////////////////////  리뷰 끝



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                login();
            }

        }

    }

    public void login() {

        /**
         *  db파괴
         *  테이블 레코드 초기에 다 날린다
         *  어플을 처음깔았을 시 -> getWritableDatabase() 호출시 onCreate가 콜백으로 불린다 -> 테이블생성
         *  어플 존재시 -> 테이블은 무조건 존재하므로 getWritableDatabase()호출되면 콜백 생략 -> 테이블 레코드 날아감
         */
        System.out.println("login()진입");
        database = databaseHelper.getWritableDatabase();

        database.execSQL("DELETE FROM users");
        database.execSQL("DELETE FROM restaurant");
        database.execSQL("DELETE FROM food");
        database.execSQL("DELETE FROM review");
        database.execSQL("DELETE FROM ordernum");

        database.close();


        // db열어서 유저일단 저장
        userDataSource.open();
        userDataSource.addUser(userModel);
        userDataSource.close();

        addRestaurantData(restaurantModels);
        addFoodData(foodModels);
        addOrderData(orderModels);
        addReviewData(reviewModels);
    }

    /**
     *  레스토랑 데이터 sqlite에 add 하고 addRestaurant 으로 리스트뷰에 뿌림
     * @param restaurantModels
     */
    public void addRestaurantData(RestaurantModel[] restaurantModels) {


        restaurantDataSource.open();

        for(int i=0; i<restaurantModels.length; i++) {
            restaurantDataSource.addRestaurant(restaurantModels[i]);
        }

        restaurantDataSource.close();
    }

    public void addFoodData(FoodModel[] foodModels) {

        foodDataSource.open();

        for(int i=0; i<foodModels.length; i++) {
            foodDataSource.addFood(foodModels[i]);
        }

        foodDataSource.close();
    }

    public void addOrderData(OrderModel[] orderModels) {
        orderDataSource.open();

        for(int i=0; i<orderModels.length; i++) {
            orderDataSource.addOrder(orderModels[i]);
        }
        orderDataSource.close();
    }

    public void addReviewData(ReviewModel[] reviewModels) {
        reviewDataSource.open();

        for(int i=0; i<reviewModels.length; i++) {
            reviewDataSource.addReview(reviewModels[i]);
        }
        reviewDataSource.close();
    }
}
