package com.mysampleapp.demo.content;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.mobilehelper.auth.ImageDownloader;
import com.mysampleapp.R;
import com.mysampleapp.demo.model.RecipeItem;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 2017/6/9.
 */

public class StepAdapter extends BaseAdapter {
    private List<String> mMessageList;
    private LayoutInflater mMyInflater;

    public StepAdapter(Context c, List<String> list) {
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

        convertView = mMyInflater.inflate(R.layout.list_step, null);
        String message = mMessageList.get(position);
        List<String> stepList = Arrays.asList(message.split("\n"));
        String stepStr = "";
        for(int i=0; i<stepList.size(); i++){
            String step =  stepList.get(i);
            if(i!=0){
                stepStr += "\n\n";
            }
            stepStr += step.substring(0, 1).toUpperCase() + step.substring(1);
        }

        TextView stepTxt = (TextView) convertView.findViewById(R.id.textView);

        stepTxt.setText(stepStr);

        return convertView;
    }

}
