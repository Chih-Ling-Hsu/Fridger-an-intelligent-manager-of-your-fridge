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

import java.util.List;

/**
 * Created by User on 2017/6/6.
 */

public class RecipeItemAdapter extends BaseAdapter {
    private List<RecipeItem> mMessageList;
    private LayoutInflater mMyInflater;

    public RecipeItemAdapter(Context c, List<RecipeItem> list) {
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

    public void setList(List<RecipeItem> list) {
        this.mMessageList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mMyInflater.inflate(R.layout.list_item_recipe, null);
        RecipeItem message = mMessageList.get(position);

        TextView nameTxt = (TextView) convertView.findViewById(R.id.txt_name);
        //TextView ingTxt = (TextView) convertView.findViewById(R.id.txt_ingredients);
        ImageView recipeImg = (ImageView) convertView.findViewById(R.id.img_recipe);

        nameTxt.setText(message.getName());
        /*if(message.getIngredients().length()>50){
            ingTxt.setText(message.getIngredients().substring(0,50)+"...");
        }
        else {
            ingTxt.setText(message.getIngredients());
        }*/
        new DownloadImageTask(recipeImg).execute(message.getImgUrl());


        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            ImageDownloader imgUtil = new ImageDownloader();
            imgUtil.loadImageFromUrl(imageUrl);
            while(imgUtil.getImage()==null){

            }
            Log.e("Async OK", "OKKKKKKKKKKKKK");
            return imgUtil.getImage();
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
