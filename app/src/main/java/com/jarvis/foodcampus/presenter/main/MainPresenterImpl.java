package com.jarvis.foodcampus.presenter.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.view.main.MainView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JunHo on 2016-10-22.
 */

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private Context context;
    private SharedPreferences sharedPreferences;
    private String serialNumber;
    private String nickName;

    public MainPresenterImpl(MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;

        checkUser(); // 유저 체킹 메세지
    }

    private void checkUser() {
        sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
        serialNumber = sharedPreferences.getString("serial_number",null);
        nickName = sharedPreferences.getString("nickname",null);
        mainView.showMessage("serial_number: "+serialNumber+"\n"+nickName+"님 안녕");
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
        } else if (v.getId() == R.id.chicken_btn) {
            mainView.showMessage("Chicken Click");
        }else if (v.getId() == R.id.noodle_btn) {
            mainView.showMessage("Noodle Click");
        }else if (v.getId() == R.id.hansik_btn) {
            mainView.showMessage("Hansik Click");
        }

        return "fine";
    }
}
