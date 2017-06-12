package com.mysampleapp.demo.content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysampleapp.R;
import com.mysampleapp.demo.model.SpaceItem;

import java.util.List;

/**
 * Created by User on 2017/5/29.
 */

public class IngredientItemAdapter extends BaseAdapter {
    private List<SpaceItem> mMessageList;
    private LayoutInflater mMyInflater;

    public IngredientItemAdapter(Context c, List<SpaceItem> list) {
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

        convertView = mMyInflater.inflate(R.layout.list_item_ingredient, null);
        SpaceItem message = mMessageList.get(position);

        TextView itemTxt = (TextView) convertView.findViewById(R.id.txt_item);
        TextView dateTxt = (TextView) convertView.findViewById(R.id.txt_date);
        ImageView checkImg = (ImageView) convertView.findViewById(R.id.img_check);

        itemTxt.setText(message.getItem());
        dateTxt.setText(message.getExpDate());
        if(message.getCheck()){
            checkImg.setImageResource(R.mipmap.pin);
        }
        else{
            checkImg.setImageResource(R.mipmap.graypin);
        }


        return convertView;
    }
}