package com.mysampleapp.demo.content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysampleapp.R;

import java.util.List;

/**
 * Created by User on 2017/6/9.
 */

public class IngredientAdapter extends BaseAdapter {
    private List<String> mMessageList;
    private LayoutInflater mMyInflater;

    public IngredientAdapter(Context c, List<String> list) {
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

    public void setList(List<String> list) {
        this.mMessageList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mMyInflater.inflate(R.layout.list_ingredient, null);
        String message = mMessageList.get(position);

        TextView ingTxt = (TextView) convertView.findViewById(R.id.textView);

        ingTxt.setText(message);

        return convertView;
    }

}
