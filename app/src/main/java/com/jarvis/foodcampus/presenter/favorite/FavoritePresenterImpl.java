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

        Integer[] resIds; // 일단 레스토랑 아이디를 배열로 갖고 있어야 됨

        database = databaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM favorite";

        Cursor result = database.rawQuery(sql, null);
        favoriteModels = new FavoriteModel[result.getCount()];
        resIds = new Integer[result.getCount()];

        try {
            if (result != null && result.moveToFirst()) {
                for (int i = 0; i < result.getCount(); i++) {
                    Integer resId = result.getInt(1);
                    resIds[i] = resId;

                    result.moveToNext();
                }
            }

        } finally {
            if (result != null && !result.isClosed()) {
                result.close();
            }
        }
        database.close();


        /**
         *  resId 기준으로 레스토랑 테이블에 접근해서 레스토랑 이름과 카테고리를 가져와서 모델을 초기화한다
         */

        database = databaseHelper.getReadableDatabase();
        favoriteModels = new FavoriteModel[resIds.length];

        for(int i=0; i<resIds.length; i++) {
            String sqlRes = "SELECT * FROM restaurant WHERE restaurant_id = " + resIds[i];

            Cursor cursor = database.rawQuery(sqlRes, null);

            try {
                if (cursor.moveToFirst()) {
                    int categoryId = cursor.getInt(2);
                    String restaurantName = cursor.getString(3);

                    // 로컬에서 뿌리는건 user_id가 필요없으니 뷰쪽으로 던지는 favorite모델에는 그냥 1로 넣었음
                    favoriteModels[i] = new FavoriteModel(1, resIds[i], categoryId, restaurantName);
                    favoriteView.add(favoriteModels[i]);

                    cursor.moveToNext();
                }
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }

        database.close();
    }
}
