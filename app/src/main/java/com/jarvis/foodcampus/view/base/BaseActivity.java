package com.jarvis.foodcampus.view.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.presenter.base.BasePresenter;
import com.jarvis.foodcampus.presenter.base.BasePresenterImpl;
import com.jarvis.foodcampus.view.login.LoginActivity;
import com.jarvis.foodcampus.view.main.MainActivity;
import com.jarvis.foodcampus.view.restaurant.RestaurantActivity;
import com.jarvis.foodcampus.view.schoolfood.SchoolFoodActivity;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.helper.log.Logger;

/**
 * Created by JunHo on 2016-10-04.
 */

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,BaseActivityView {

    private Toolbar mActionBarToolbar;
    private DrawerLayout mDrawerLayout;
    protected NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;
    private BasePresenter basePresenter;


    /**
     *  헬퍼 함수 (툴바를 쓸지)
     * @return
     */
    public boolean useToolbar() {
        return true;
    }

    /**
     * Helper method to allow child classes to opt-out of having the
     * hamburger menu.
     *
     * @return
     */
    public boolean useDrawerToggle() {
        return true;
    }


    // 각자에 맞는 contentlayout을 보여주기위해 오버라이딩함
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID); // <-- 원래 기능
        getActionBarToolbar(); // 툴바 윗대가리 추가하려고
        setupNavDrawer(); // 드로우어 옆으로 튀나오게 하려고
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        basePresenter = new BasePresenterImpl(this, getApplicationContext());

        /** 폰트적용부분 -- 나중에 위치수정해야함
         Typekit.getInstance()
         .addNormal(Typekit.createFromAsset(this, "fonts/NanumGothicBold.ttf"))
         .addCustom1(Typekit.createFromAsset(this, "fonts/InterparkGothicBold.ttf"))
         .addCustom2(Typekit.createFromAsset(this, "fonts/JejuGothic.ttf"))
         .addCustom3(Typekit.createFromAsset(this, "fonts/beantown.regular.ttf"));
         */
    }

    /**
     *  툴바를 가져온다
     * @return
     */
    @Override
    public Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
            if (mActionBarToolbar != null) {
                // Depending on which version of Android you are on the Toolbar or the ActionBar may be
                // active so the a11y description is set here.
                mActionBarToolbar.setNavigationContentDescription(getResources()
                        .getString(R.string.navdrawer_description_a11y));
                //setSupportActionBar(mActionBarToolbar);

                if (useToolbar()) {
                    setSupportActionBar(mActionBarToolbar);
                } else {
                    mActionBarToolbar.setVisibility(View.GONE);
                }

            }
        }

        return mActionBarToolbar;
    }

    /**
     *  네비게이션 드로어를 설정한다
     */
    @Override
    public void setupNavDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        if (useDrawerToggle()) {
            mToggle = new ActionBarDrawerToggle(
                    this, mDrawerLayout, mActionBarToolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);
            mDrawerLayout.setDrawerListener(mToggle);
            mToggle.syncState();
        } else if (useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(ContextCompat
                    .getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    */

    /**
     *  어떤 메뉴가 선택되었는지
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.nav_home:
                createBackStack(new Intent(this, MainActivity.class));
                finish();
                break;

            case R.id.nav_dorm:
                //createBackStack(new Intent(this, DormActivity.class));
                createBackStack(new Intent(this, SchoolFoodActivity.class));
                finish();
                break;

            case R.id.nav_pizza:
                intent = new Intent(this,RestaurantActivity.class);
                intent.putExtra("restaurant","pizza");
                createBackStack(intent);
                finish();
                break;

            case R.id.nav_chicken:
                intent = new Intent(this,RestaurantActivity.class);
                intent.putExtra("restaurant","chicken");
                createBackStack(intent);
                finish();
                break;

            case R.id.nav_korean:
                //createBackStack(new Intent(this, KoreanActivity.class));
                intent = new Intent(this,RestaurantActivity.class);
                intent.putExtra("restaurant","korean");
                createBackStack(intent);
                finish();
                break;

            case R.id.nav_chinese:
                //createBackStack(new Intent(this, FlourActivity.class));
                intent = new Intent(this,RestaurantActivity.class);
                intent.putExtra("restaurant","chinese");
                createBackStack(intent);
                finish();
                break;

            case R.id.nav_jokbal:
                //createBackStack(new Intent(this, JokbalActivity.class));
                finish();
                break;

            case R.id.nav_logout:

                basePresenter.logout();
                break;
            case R.id.nav_withdrawal:

                onClickUnlink();
                break;

        }

        closeNavDrawer();

        return true;
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    /**
     * Enables back navigation for activities that are launched from the NavBar. See
     * {@code AndroidManifest.xml} to find out the parent activity names for each activity.
     *
     * @param intent
     */
    @Override
    public void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder builder = TaskStackBuilder.create(this);
            builder.addNextIntentWithParentStack(intent);
            builder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

/*
    // 프로필 사진 클릭
    public void onClick(View v) {
        switch (v.getId()) {
            // 프로필액티비티 만들고 넣으면됨
            case R.id.nav_imageView:
                //Intent intent = new Intent(this, ProfileActivity.class);
                //intent.putExtra("UserObject", userObject);
                //createBackStack(intent);
                break;
        }
    }
*/
    @Override
    public void redirectLoginActivity() {
        createBackStack(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     *  ?????????
     */
    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManagement.requestUnlink(new UnLinkResponseCallback() {
                                    @Override
                                    public void onFailure(ErrorResult errorResult) {
                                        Logger.e(errorResult.toString());
                                    }

                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) {
                                        redirectLoginActivity();
                                    }

                                    @Override
                                    public void onNotSignedUp() {
//                                        redirectSignupActivity();
                                    }

                                    @Override
                                    public void onSuccess(Long userId) {
                                        redirectLoginActivity();
                                    }
                                });
                                dialog.dismiss();
                            }
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }
}