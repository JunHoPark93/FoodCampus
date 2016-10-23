package com.jarvis.foodcampus.view.base;

import android.content.Intent;
import android.support.v7.widget.Toolbar;

/**
 * Created by JunHo on 2016-10-21.
 */

public interface BaseActivityView {

    boolean useToolbar();

    boolean useDrawerToggle();

    void setContentView(int layoutResID);

    void setupNavDrawer();

    void onBackPressed();

    void closeNavDrawer();

    void createBackStack(Intent intent);

    Toolbar getActionBarToolbar();

    void redirectLoginActivity();
}
