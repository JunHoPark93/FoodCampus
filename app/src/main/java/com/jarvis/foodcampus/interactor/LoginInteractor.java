package com.jarvis.foodcampus.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.DB.FoodDataSource;
import com.jarvis.foodcampus.DB.RestaurantDataSource;
import com.jarvis.foodcampus.DB.UserDataSource;
import com.jarvis.foodcampus.model.RestaurantModel;
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
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Context context;
    private UserModel userModel;
    private LoginRequest loginRequest;

    private RestaurantModel[] restaurantModels; // 서버에서 받아온거 내장에 박기위한 모델

    public LoginInteractor(Context context) {

        userDataSource = new UserDataSource(context);
        restaurantDataSource = new RestaurantDataSource(context);
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
                    //JSONArray reviews = jObject.getJSONArray("reviews");

                    System.out.println(restaurants);
                    System.out.println(foods);
                    //System.out.println(reviews);

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
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //login(userModel, restaurantModels);

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

        //database.close();

        // db열어서 유저일단 저장
        userDataSource.open();
        userDataSource.addUser(userModel);
        //userDataSource.close();


        System.out.println("login()내부 레스토랑"+restaurantModels[0].getRestaurantName());
        // 레스토랑 데이터 저장
        addRestaurantData(restaurantModels);

        //addRestaurantData(new RestaurantModel(1,1,"가마손","ㅇㅇ","010","22:00","11:00"));

//        restaurantDataSource.open();
//        RestaurantModel restaurantModel = new RestaurantModel(1,1,"가마손","ㅇㅇ","010","22:00","11:00");
//        restaurantDataSource.addRestaurant(restaurantModel);
//        restaurantModel = new RestaurantModel(2,3,"두목치킨","맛잇어","0109999","11:00","10:00");
//        restaurantDataSource.addRestaurant(restaurantModel);

        /**
         *  내장db에 다 박았어
         */
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

        //restaurantDataSource.addRestaurant(restaurantModels);
    }

}
