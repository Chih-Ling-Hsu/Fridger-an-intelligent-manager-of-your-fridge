package com.mysampleapp.demo.model;

import android.graphics.drawable.Drawable;

/**
 * Created by User on 2017/6/6.
 */

public class RecipeItem {
    private String name;
    private String ingredients;
    private String img_url;
    private String steps;

    public RecipeItem(String name, String ingredients, String img_url, String steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.img_url = img_url;
        this.steps=steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }

    public String getImgUrl() {
        return img_url;
    }

    public void setImgUrl(String img_url){
        this.img_url = img_url;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps){
        this.steps = steps;
    }
}
