package com.mysampleapp.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mysampleapp.R;
import com.mysampleapp.demo.content.IngredientItemAdapter;
import com.mysampleapp.demo.content.SpaceItemAdapter;
import com.mysampleapp.demo.model.SpaceItem;
import com.mysampleapp.demo.service.HttpURLConnectionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

/**
 * Created by User on 2017/5/29.
 */

public class RecipeFragment  extends DemoFragmentBase {

    private static final String ARGUMENT_DEMO_FEATURE_NAME = "demo_feature_name";
    private static final double maxVisibleDemos = 3.5;
    private List<SpaceItem> spaceItemList;
    private static int pos;
    private static IngredientItemAdapter adapter;
    private TextView resultTxt;
    private ListView mListView;

    public static RecipeFragment newInstance(final String demoFeatureName) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(RecipeFragment.ARGUMENT_DEMO_FEATURE_NAME, demoFeatureName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle args = getArguments();
        final String demoFeatureName = args.getString(ARGUMENT_DEMO_FEATURE_NAME);
        final DemoConfiguration.DemoFeature demoFeature = DemoConfiguration.getDemoFeatureByName(
                demoFeatureName);

        // Set the title for the instruction fragment.
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(demoFeature.titleResId);
        }

        // Initialize views
        mListView = (ListView) view.findViewById(R.id.list_ingredient);
        final LinearLayout recommendBtn = (LinearLayout) view.findViewById(R.id.btn_recommnend);
        resultTxt = (TextView) view.findViewById(R.id.txt_result);

        new RequestTask("/get-ingredient").execute();

        /*spaceItemList = new ArrayList<SpaceItem>();
        spaceItemList.add(new SpaceItem("0","Apple", "2017/05/29", "http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png"));
        spaceItemList.add(new SpaceItem("0","Banana", "2017/05/29", "http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png"));
        spaceItemList.add(new SpaceItem("0","Apple", "2017/05/29", "http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png"));
        spaceItemList.add(new SpaceItem("0","Banana", "2017/05/29", "http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png"));
        spaceItemList.add(new SpaceItem("0","Apple", "2017/05/29", "http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png"));
        spaceItemList.add(new SpaceItem("0","Banana", "2017/05/29", "http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png"));

        // Set items to the list view
        adapter = new IngredientItemAdapter(this.getContext(), spaceItemList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SpaceItem item = spaceItemList.get(position);
                ImageView checkImg = (ImageView) view.findViewById(R.id.img_check);
                if(item.getCheck()){
                    item.setCheck(false);
                    checkImg.setImageResource(R.mipmap.graypin);
                }
                else{
                    item.setCheck(true);
                    checkImg.setImageResource(R.mipmap.pin);
                }
                spaceItemList.set(position, item);
                //mListView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
            }
        });*/

        recommendBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                List<String> checkList = new ArrayList<String>();
                for(SpaceItem item: spaceItemList){
                    if(item.getCheck()) {
                        checkList.add(item.getItem());
                    }
                }
                String ingList =  "{ \"recipe\":" + checkList.toString().replace(",", "\",\"").replace("[","[\"").replace("]","\"]") + "}";
                new RequestTask("/search-recipe").execute(ingList);

                /*Toast.makeText(getActivity(), checkList.toString(), Toast.LENGTH_LONG).show();
                final Fragment fragment = RecipeListFragment.newInstance("recipe_list");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_container, fragment, "recipe_list")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();*/
            }
        });

    }

    private class RequestTask extends AsyncTask<String, Void, String> {
        String api_url;

        public RequestTask(String api_url) {
            this.api_url = "https://f7lw9xhg1d.execute-api.us-east-1.amazonaws.com/develop"+api_url;
        }

        protected String doInBackground(String... args) {
            String postMsg = "";
            if(args.length>0){
                postMsg = args[0];
            }
            String response = "[]";

            try {
                HttpURLConnectionHandler httpUtil = new HttpURLConnectionHandler();
                httpUtil.sendPost(api_url, postMsg);
                while(httpUtil.complete==false){

                }
                response = httpUtil.getResponse();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Asynk Error", e.toString());
            }

            return response;
        }

        protected void onPostExecute(String response) {
            Log.e("Async OK", response);
            try{
                if (this.api_url.indexOf("/get-ingredient")!=-1) {
                    if (response.equals("[]")) {
                        resultTxt.setText("Your fridge is empty now.");
                    } else {
                        rendarView(response);
                    }
                } else {
                    if (response.equals("[]")) {
                        Toast.makeText(getActivity(), "Ooops, we can not recommend you any recipe with these ingredients.", Toast.LENGTH_LONG).show();
                    } else {
                        final Fragment fragment = RecipeListFragment.newInstance(response);

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_fragment_container, fragment, "recipe_list")
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Asynk Error", e.toString());
            }
        }
    }


    private void rendarView(String response) throws JSONException {
        JSONArray responseArray = new JSONArray(response);
        Log.e("JSONNNNN", String.valueOf(responseArray.length()));
        spaceItemList = new ArrayList<SpaceItem>();
        for (int i = 0; i < responseArray.length(); i++) {
            JSONObject item = responseArray.getJSONObject(i);
            String imgUrl = "https://s3.amazonaws.com/"+item.getString("bucketname")+"/"+item.getString("imagename");
            spaceItemList.add(new SpaceItem(item.getString("iid"), item.getString("reko_result"), item.getString("time"), imgUrl));
        }
        // Set items to the list view
        adapter = new IngredientItemAdapter(this.getContext(), spaceItemList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                SpaceItem item = spaceItemList.get(position);
                ImageView checkImg = (ImageView) view.findViewById(R.id.img_check);
                if(item.getCheck()){
                    item.setCheck(false);
                    checkImg.setImageResource(R.mipmap.graypin);
                }
                else{
                    item.setCheck(true);
                    checkImg.setImageResource(R.mipmap.pin);
                }
                spaceItemList.set(position, item);
                //mListView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
            }
        });

    }


}
