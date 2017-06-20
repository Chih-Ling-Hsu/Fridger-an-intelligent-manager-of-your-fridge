package com.mysampleapp.demo;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.mysampleapp.R;
import com.mysampleapp.demo.content.IngredientItemAdapter;
import com.mysampleapp.demo.content.RecipeItemAdapter;
import com.mysampleapp.demo.model.RecipeItem;
import com.mysampleapp.demo.model.SpaceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2017/6/6.
 */

public class RecipeListFragment extends DemoFragmentBase {
    private static final String RECIPE_LIST = "recipe_list";
    private static final double maxVisibleDemos = 3.5;
    private List<RecipeItem> recipeItemList;
    private static int pos;
    private static RecipeItemAdapter adapter;

    public static RecipeListFragment newInstance(final String response) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putString(RecipeListFragment.RECIPE_LIST, response);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Initialize views
        final ListView mListView = (ListView) view.findViewById(R.id.list_recipe);
        recipeItemList = new ArrayList<RecipeItem>();

        /*recipeItemList.add(new RecipeItem("Apple", "apple, banana, pie, water", "https://www.google.ca/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", "do step1,do stpe 2,do stpe 2,do stpe 2,do stpe 2,do stpe 2,do stpe 2,do stpe 2"));
        recipeItemList.add(new RecipeItem("Apple", "apple, banana, pie, water", "https://www.google.ca/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", "do step1,do stpe 2"));
        recipeItemList.add(new RecipeItem("Apple", "apple, banana, pie, water", "https://www.google.ca/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", "do step1,do stpe 2"));*/


        final Bundle args = getArguments();
        String response = args.getString(RecipeListFragment.RECIPE_LIST);
        try {
            JSONArray responseArray = new JSONArray(response);
            Log.e("JSONNNNN", String.valueOf(responseArray.length()));
            for (int i = 0; i < responseArray.length(); i++) {
                Log.e("JSONNNNN", responseArray.getJSONObject(i).toString());
                JSONObject item = responseArray.getJSONObject(i).getJSONObject("fields");
                Log.e("JSONNNNN", responseArray.getJSONObject(i).getJSONObject("fields").toString());
                recipeItemList.add(new RecipeItem(item.getString("recipe_name").replace("[\"", "").replace("\"]", ""),
                                    item.getString("ingredients").replace("[\"", "").replace("\"]", "").replace("\\",""),
                                    item.getString("img_url").replace("[\"", "").replace("\"]", "").replace("\\",""),
                                    item.getString("steps").replace("[\"", "").replace("\"]", "").replace("\\","").replace(".  ",".\n")));
                Log.e("Rcipe LIST", recipeItemList.toString());
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        // Set items to the list view
        adapter = new RecipeItemAdapter(this.getContext(), recipeItemList);
        mListView.setAdapter(adapter);


        // TODO
        // 1. Use onclick listener here
        // 2. Create alarm onclick listener

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RecipeItem item = recipeItemList.get(position);
                final Fragment fragment = RecipeItemFragment.newInstance(item);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.main_fragment_container, fragment, "recipe_list")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        });

    }
}
