package com.jarvis.foodcampus.presenter.restaurant;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.model.RestaurantModel;

import java.util.ArrayList;

/**
 * Created by JunHo on 2016-10-22.
 */

public class RestaurantAdpater extends BaseAdapter {

    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<RestaurantModel> listViewItemList = new ArrayList<RestaurantModel>() ;
    //private RestaurantView restaurantView; // 넘겨받은 뷰

    // ListViewAdapter의 생성자
    public RestaurantAdpater() {
        //this.restaurantView = restaurantView;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴 : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴 : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // layout을 inflate하여 convertView 참조 획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_restaurant_onecol, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.pizza_pic) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.pizza_name) ;
        //TextView extraTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        //listViewItemList에서 position에 위치한 데이터 참조 획득
        RestaurantModel listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIconDrawable());
        titleTextView.setText(listViewItem.getTitleStr());
        //extraTextView.setText(listViewItem.getExtra());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템의 ID를 리턴 : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(Drawable icon, String title, String extra) {
        RestaurantModel item = new RestaurantModel();

        item.setIconDrawable(icon);
        item.setTitleStr(title);
        item.setExtra(extra);

        listViewItemList.add(item);
    }
}
