package com.jarvis.foodcampus.presenter.schoolfood;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jarvis.foodcampus.view.schoolfood.HakGwanFragment;
import com.jarvis.foodcampus.view.schoolfood.MyongJinDangFragment;

/**
 * Created by JunHo on 2016-11-15.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    int tabCount;
    private Fragment result;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.tabCount=numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                result = new HakGwanFragment();
                break;

            case 1:
                result = new MyongJinDangFragment();
                break;
        }

        return result;
    }

    @Override
    public int getCount(){

        return tabCount;
    }
}
