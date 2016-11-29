package com.jarvis.foodcampus.presenter.favorite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jarvis.foodcampus.DB.DatabaseHelper;
import com.jarvis.foodcampus.model.FavoriteModel;
import com.jarvis.foodcampus.view.favorite.FavoriteView;

/**
 * Created by JunHo on 2016-11-29.
 */

public class FavoritePresenterImpl implements FavoritePresenter{

    private FavoriteView favoriteView;
    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private FavoriteModel[] favoriteModels;

    public FavoritePresenterImpl(FavoriteView favoriteView, Context context) {
        this.favoriteView = favoriteView;
        this.context = context;

        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
    }

    @Override
    public void initData() {
        /**
         * sqlite 요청해서 add로 view쪽에 던져
         */

        database = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM favorite";

        Cursor result = database.rawQuery(sql, null);
        favoriteModels = new FavoriteModel[result.getCount()];
        System.out.println("즐겨차기커서길이"+result.getCount());
        result.moveToFirst();
        System.out.println("커서앞으로이동");

        try {
            if (result != null && result.moveToFirst()) {
                for (int i = 0; i < result.getCount(); i++) {
                    Integer resId = result.getInt(1);
                    System.out.println("좆같네"+result.getInt(0) + result.getInt(1) + result.getInt(2));
                    /**
                     *  ## 유저아이디는 받는 뷰쪽에서 필요없어서 그냥 1로 넣음
                     */
                    favoriteModels[i] = new FavoriteModel(1, resId);
                    favoriteView.add(favoriteModels[i]);

                    result.moveToNext();
                }
            }

        } finally {
            if (result != null && !result.isClosed()) {
                result.close();
            }
        }
        database.close();
    }
}
