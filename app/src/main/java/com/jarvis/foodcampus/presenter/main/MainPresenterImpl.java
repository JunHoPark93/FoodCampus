package com.jarvis.foodcampus.presenter.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.view.main.MainView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JunHo on 2016-10-22.
 */

public class MainPresenterImpl implements MainPresenter {

    /**
     *  테스트
     */
    private String dbName = "FoodCampus.db";

    private MainView mainView;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String serialNumber;
    private String nickName;

    public MainPresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;

        checkUser(); // 유저 체킹 메세지
        dbCheck();
    }

    private void checkUser() {
        sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        serialNumber = sharedPreferences.getString("serial_number",null);
        nickName = sharedPreferences.getString("nickname",null);
        mainView.showMessage("serial_number: "+serialNumber+"\n"+nickName+"님 안녕");
    }

    /**
     *  db 조회 테스트임 하는 방식 익히고 지울 것
     */
    private void dbCheck() {

        SQLiteDatabase db;
        //db = SQLiteDatabase.openDatabase(dbName, ,1);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getReadableDatabase();

        System.out.println("dbCheck진입");
        String sql = "select * from " + "users";
        Cursor result = db.rawQuery(sql, null);

        System.out.println("길이"+result.getCount());

        // result(Cursor 객체)가 비어 있으면 false 리턴
        try {
            if (result.moveToFirst()) {
                long id = result.getLong(1);
                String voca = result.getString(2);
                Toast.makeText(context, "DB조회테스트 id= " + id + " num=" + voca, Toast.LENGTH_LONG).show();
            }
        } finally {
            if (result != null && !result.isClosed()) {
                result.close();
            }
        }
    }

    /**
     *  나중에 어떤 버튼이 눌리는지에 따라 수정
     * @param v
     * @return
     */
    @Override
    public String mainBtnCheck(View v) {
        ImageButton button = (ImageButton) v;
        if(v.getId() == R.id.pizza_btn) {
            mainView.showMessage("Pizza Click");
            return "pizza";
        } else if (v.getId() == R.id.chicken_btn) {
            mainView.showMessage("Chicken Click");
            return "chicken";
        } else if (v.getId() == R.id.noodle_btn) {
            mainView.showMessage("Chinese Click");
            return "chinese";
        } else if (v.getId() == R.id.hansik_btn) {
            mainView.showMessage("Korean Click");
            return "korean";
        } else if(v.getId() == R.id.pig_btn) {
            mainView.showMessage("Night Click");
            return "night";
        } else if(v.getId() == R.id.japanese_btn) {
            mainView.showMessage("Japanese Click");
            return "japanese";
        } else if(v.getId() == R.id.main_today_pick_btn) {
            mainView.showMessage("Random Click");
            return "random";
        } else {
            return "failed"; // 예외처리 어떻게할까?
        }
    }
}
