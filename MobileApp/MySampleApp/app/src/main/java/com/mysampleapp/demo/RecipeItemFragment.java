package com.mysampleapp.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.amazonaws.mobilehelper.auth.ImageDownloader;
import com.mysampleapp.R;
import com.mysampleapp.demo.content.IngredientAdapter;
import com.mysampleapp.demo.content.RecipeItemAdapter;
import com.mysampleapp.demo.content.StepAdapter;
import com.mysampleapp.demo.model.RecipeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 2017/6/6.
 */

public class RecipeItemFragment extends DemoFragmentBase{
    private static final String RECIPE_NAME = "recipe_name";
    private static final String RECIPE_INGREDIENT = "recipe_ingredients";
    private static final String RECIPE_IMG = "recipe_img_url";
    private static final String RECIPE_STEPS = "recipe_steps";
    private TextView nameTxt;
    private TextView btnTxt;
    private ListView ingListView;
    private ListView mListView;
    private ImageView recipeImg;

    public static RecipeItemFragment newInstance(final RecipeItem item) {
        RecipeItemFragment fragment = new RecipeItemFragment();
        Bundle args = new Bundle();
        args.putString(RecipeItemFragment.RECIPE_NAME, item.getName());
        args.putString(RecipeItemFragment.RECIPE_INGREDIENT, item.getIngredients());
        args.putString(RecipeItemFragment.RECIPE_IMG, item.getImgUrl());
        args.putString(RecipeItemFragment.RECIPE_STEPS, item.getSteps());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_item, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle args = getArguments();
        final RecipeItem item = new RecipeItem(args.getString(RECIPE_NAME), args.getString(RECIPE_INGREDIENT), args.getString(RECIPE_IMG), args.getString(RECIPE_STEPS));

        // Initialize views
        nameTxt = (TextView) view.findViewById(R.id.txt_recipename);
        btnTxt = (TextView) view.findViewById(R.id.btn_show_ing);
        ingListView = (ListView) view.findViewById(R.id.txt_ingredients);
        mListView = (ListView) view.findViewById(R.id.list_steps);
        recipeImg = (ImageView) view.findViewById(R.id.img_recipe);

        nameTxt.setText(item.getName());
        new DownloadImageTask(recipeImg).execute(item.getImgUrl());
        List<String> ingList = Arrays.asList(item.getIngredients().split("\",\""));
        List<String> stepList = Arrays.asList(item.getSteps().split("\",\""));

        // Set items to the list view
        IngredientAdapter ingAdapter = new IngredientAdapter(this.getContext(), ingList);
        ingListView.setAdapter(ingAdapter);
        StepAdapter adapter = new StepAdapter(this.getContext(), stepList);
        mListView.setAdapter(adapter);

        btnTxt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(btnTxt.getText().toString().trim().equals("Show Ingredients")) {
                    ingListView.setVisibility(View.VISIBLE);
                    btnTxt.setText("Hide Ingredients");
                }
                else{
                    ingListView.setVisibility(View.GONE);
                    btnTxt.setText("Show Ingredients");
                }
            }
        });

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

