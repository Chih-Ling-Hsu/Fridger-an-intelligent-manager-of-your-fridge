package com.mysampleapp.demo.model;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by User on 2017/5/28.
 */

public class SpaceItem {
    private String item;
    private String exp_date;
    private String img_url;
    private Drawable img;
    private boolean checked;
    private String id;

    public SpaceItem(String id, String item, String exp_date, String img_url) {
        this.id = id;
        this.item = item;
        this.exp_date = exp_date;
        this.img_url = img_url;
        this.img = null;
        this.checked = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getExpDate() {
        return exp_date;
    }

    public void setExpDate(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getImgUrl() {
        return img_url;
    }

    public void setImgUrl(String img_url) {
        this.img_url = img_url;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public boolean getCheck() {
        return checked;
    }

    public void setCheck(boolean check) {
        this.checked = check;
    }
}
