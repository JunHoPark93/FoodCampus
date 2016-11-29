package com.jarvis.foodcampus.presenter.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.model.FavoriteModel;

import java.util.ArrayList;

/**
 * Created by JunHo on 2016-11-29.
 */

public class FavoriteAdapter extends BaseAdapter{

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<FavoriteModel> listViewItemList = new ArrayList<FavoriteModel>();

    // ListViewAdapter의 생성자
    public FavoriteAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴 : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴 : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // layout을 inflate하여 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.favorite_layout_onecol, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.favorite_name);

        //listViewItemList에서 position에 위치한 데이터 참조 획득
        FavoriteModel listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        //iconImageView.setImageDrawable(listViewItem.getIconDrawable());
        titleTextView.setText(String.valueOf(listViewItem.getRestaurantId()));

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템의 ID를 리턴 : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(FavoriteModel model) {

        /**
         *  모델을 넘겨받아서 애드하자
         */
        listViewItemList.add(model);
    }
}
