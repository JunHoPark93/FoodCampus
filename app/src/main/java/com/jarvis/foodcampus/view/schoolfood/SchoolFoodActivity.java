package com.jarvis.foodcampus.view.schoolfood;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.presenter.schoolfood.TabPagerAdapter;
import com.jarvis.foodcampus.view.base.BaseActivity;


/**
 * Created by JunHo on 2016-11-06.
 */

public class SchoolFoodActivity extends BaseActivity implements SchoolFoodView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolfood);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.schoolfood_tab);

        tabLayout.addTab(tabLayout.newTab().setText("학관"));
        tabLayout.addTab(tabLayout.newTab().setText("명진당"));

        final ViewPager viewPager = (ViewPager)findViewById(R.id.schoolfood_pager);
        final PagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab){
            }
        });

        //Set nav drawer selected to first item in list
        mNavigationView.getMenu().getItem(0).setChecked(true);

    }
}
