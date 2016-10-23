package com.jarvis.foodcampus.presenter.base;

import android.content.Context;

import com.jarvis.foodcampus.view.base.BaseActivityView;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

/**
 * Created by JunHo on 2016-10-22.
 */

public class BasePresenterImpl implements BasePresenter {

    private BaseActivityView baseActivityView;
    private Context context;

    public BasePresenterImpl(BaseActivityView baseActivityView, Context context) {
        this.baseActivityView = baseActivityView;
        this.context = context;
    }

    //카카오톡 계정 로그아웃하기
    public void logout() {
        //로그인된 사용자의 세션 연결을 해제 해줍니다.
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                /*
                 여기에서 Intent 생성해서 바로 액티비티 넘길라 하면 에러떠서
                 redirectLoginActivity() 메소드로 밖으로 뺐어요*/
                baseActivityView.redirectLoginActivity();
            }
        });
    }

}
