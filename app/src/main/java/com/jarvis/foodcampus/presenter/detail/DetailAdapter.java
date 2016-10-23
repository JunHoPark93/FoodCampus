package com.jarvis.foodcampus.presenter.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jarvis.foodcampus.R;
import com.jarvis.foodcampus.model.DetailModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by EunBi on 2016-10-23.
 */

public class DetailAdapter extends BaseExpandableListAdapter{
    private Context context;

    private ArrayList<String> arrayGroup;
    private HashMap<String, ArrayList<DetailModel>> arrayChild;

    public DetailAdapter(Context context, ArrayList<String> arrayGroup,HashMap<String, ArrayList<DetailModel>> arrayChild) {
        super();
        this.context=context;
        this.arrayGroup=arrayGroup;
        this.arrayChild=arrayChild;
    }

    public DetailAdapter(Context context){
        this.context=context;
        arrayGroup = new ArrayList<>();
        arrayChild = new HashMap<>();
    }


    @Override
    public int getGroupCount() {
        return arrayGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayChild.get(arrayGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return arrayGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupName = arrayGroup.get(groupPosition);
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v= (RelativeLayout) inflater.inflate(R.layout.detail_group, null);
        }
        TextView textChild = (TextView) v.findViewById(R.id.detail_text_group);
        textChild.setText(groupName);

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childName = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).getFood();
        String childPrice = String.valueOf(arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).getPrice());
        String childSize = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).getSize();
        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v= (RelativeLayout) inflater.inflate(R.layout.detail_child, null);
        }
        TextView nameChild = (TextView) v.findViewById(R.id.detail_child_name);
        TextView priceChild = (TextView) v.findViewById(R.id.detail_child_price);
        TextView sizeChild = (TextView) v.findViewById(R.id.detail_child_size);

        nameChild.setText(childName);
        priceChild.setText(childPrice);
        sizeChild.setText(childSize);

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void addItem(ArrayList<String> arrayGroup,HashMap<String, ArrayList<DetailModel>> arrayChild) {
            this.arrayGroup=arrayGroup;
            this.arrayChild=arrayChild;

    }

}
