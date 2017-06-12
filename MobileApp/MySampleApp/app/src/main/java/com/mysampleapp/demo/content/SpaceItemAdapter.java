package com.mysampleapp.demo.content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.mysampleapp.demo.model.SpaceItem;
import com.mysampleapp.R;

/**
 * Created by User on 2017/5/28.
 */

public class SpaceItemAdapter extends BaseAdapter {
    private List<SpaceItem> mMessageList;
    private LayoutInflater mMyInflater;

    public SpaceItemAdapter(Context c, List<SpaceItem> list) {
        this.mMessageList = list;
        mMyInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setList(List<SpaceItem> list) {
        this.mMessageList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mMyInflater.inflate(R.layout.list_item_space, null);
        SpaceItem message = mMessageList.get(position);

        TextView itemTxt = (TextView) convertView.findViewById(R.id.txt_item);
        TextView dateTxt = (TextView) convertView.findViewById(R.id.txt_date);

        itemTxt.setText(message.getItem());
        dateTxt.setText(message.getExpDate());


        return convertView;
    }
}
