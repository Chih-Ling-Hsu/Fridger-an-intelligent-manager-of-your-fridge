package com.mysampleapp.demo;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobilehelper.auth.IdentityManager;
import com.amazonaws.mobilehelper.auth.IdentityProvider;
import com.amazonaws.mobilehelper.auth.user.IdentityProfile;
import com.mysampleapp.MainActivity;
import com.mysampleapp.R;
import com.mysampleapp.demo.service.HttpURLConnectionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeDemoFragment extends DemoFragmentBase {
    /** Logging tag for this class. */
    private static final String LOG_TAG = IdentityDemoFragment.class.getSimpleName();

    /** This fragment's view. */
    private View mFragmentView;

    /** Text view for showing the introduction. */
    private TextView recipeTextView;
    private TextView dialogTextView;

    /** Image view for showing the fridge icon image. */
    private ImageView iconImageView;

    final IdentityManager identityManager =
            AWSMobileClient.defaultMobileClient().getIdentityManager();
    final IdentityProvider identityProvider =
            identityManager.getCurrentIdentityProvider();
    final IdentityProfile identityProfile = identityManager.getIdentityProfile();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // TODO
        // Create Home View

        // Inflate the layout for this fragment
        mFragmentView = inflater.inflate(R.layout.fragment_demo_home, container, false);

        return mFragmentView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inflate the layout for this fragment
        iconImageView = (ImageView)view.findViewById(R.id.imageView_demoHomeIconImage);
        dialogTextView = (TextView)view.findViewById(R.id.txt_dialog);
        recipeTextView = (TextView)view.findViewById(R.id.txt_recipe);


        if (identityProfile != null && identityProfile.getUserName() != null) {

            //dialogTextView.setText("Hi, "+identityProfile.getUserName()+".\n10 items are in your fridge.");
            new RequestTask("/get-item-count", dialogTextView).execute();
            recipeTextView.setText("Let me recommend you\nsome recipe :)");

            dialogTextView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    final Fragment fragment = SpaceFragment.newInstance("space");

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, fragment, "space")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            });

            recipeTextView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    final Fragment fragment = RecipeFragment.newInstance("recipe");

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_fragment_container, fragment, "recipe")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit();
                }
            });
        }
        else{
            dialogTextView.setText("An intelligent manager\nof your fridge.");
            recipeTextView.setText("Hello, user.\nPlease log in first.");
            //recipeTextView.setOnClickListener(MainActivity);
        }

    }


    private class RequestTask extends AsyncTask<String, Void, String> {
        String api_url;
        TextView rendarUnit;

        public RequestTask(String api_url, TextView rendarUnit) {
            this.api_url = "https://f7lw9xhg1d.execute-api.us-east-1.amazonaws.com/develop"+api_url;
            this.rendarUnit = rendarUnit;
        }

        protected String doInBackground(String... urls) {
            //String postMsg = urls[0];
            String response = "[]";

            try {
                HttpURLConnectionHandler httpUtil = new HttpURLConnectionHandler();
                httpUtil.sendPost(api_url, "");
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
            this.rendarUnit.setText("Hi, "+identityProfile.getUserName()+".\n" + response + " items are in your fridge.");
        }
    }
}
