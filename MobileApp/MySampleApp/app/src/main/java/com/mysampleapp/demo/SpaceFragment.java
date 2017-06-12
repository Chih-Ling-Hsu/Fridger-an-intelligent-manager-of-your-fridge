package com.mysampleapp.demo;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.view.View.OnClickListener;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.amazonaws.mobilehelper.auth.ImageDownloader;
import com.mysampleapp.R;
import com.mysampleapp.demo.DemoConfiguration;
import com.mysampleapp.demo.DemoFragmentBase;
import com.mysampleapp.demo.DemoInstructionFragment;
import com.mysampleapp.demo.UserSettings;
import com.mysampleapp.demo.content.SpaceItemAdapter;
import com.mysampleapp.demo.model.SpaceItem;
import com.mysampleapp.demo.service.HttpURLConnectionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by User on 2017/5/27.
 */

public class SpaceFragment  extends DemoFragmentBase {

    private static final String ARGUMENT_DEMO_FEATURE_NAME = "demo_feature_name";
    private static final double maxVisibleDemos = 3.5;
    private List<SpaceItem> spaceItemList;
    private static int pos;
    private static SpaceItemAdapter adapter;
    private TextView resultTxt;
    private ListView mListView;

    public static SpaceFragment newInstance(final String demoFeatureName) {
        SpaceFragment fragment = new SpaceFragment();
        Bundle args = new Bundle();
        args.putString(SpaceFragment.ARGUMENT_DEMO_FEATURE_NAME, demoFeatureName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_space, container, false);
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
        mListView = (ListView) view.findViewById(R.id.list_space);
        final LinearLayout alarmBtn = (LinearLayout) view.findViewById(R.id.btn_alarm);
        resultTxt = (TextView) view.findViewById(R.id.txt_result);

        new RequestTask("/get-ingredient").execute();

        /*spaceItemList = new ArrayList<SpaceItem>();
        spaceItemList.add(new SpaceItem("Apple", "2017/05/29", "https://www.google.ca/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"));
        spaceItemList.add(new SpaceItem("Banana", "2017/05/29", "https://s3.amazonaws.com/rekognition-image-102062329/image1.jpg"));
        spaceItemList.add(new SpaceItem("Apple", "2017/05/29", "https://s3.amazonaws.com/rekognition-image-102062329/image1.jpg"));
        spaceItemList.add(new SpaceItem("Banana", "2017/05/29", "https://s3.amazonaws.com/rekognition-image-102062329/image1.jpg"));
        spaceItemList.add(new SpaceItem("Apple", "2017/05/29", "https://s3.amazonaws.com/rekognition-image-102062329/image1.jpg"));
        spaceItemList.add(new SpaceItem("Banana", "2017/05/29", "https://s3.amazonaws.com/rekognition-image-102062329/image1.jpg"));

        // Set items to the list view
        adapter = new SpaceItemAdapter(this.getContext(), spaceItemList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get the layout inflater
                LayoutInflater inflater = getActivity().getLayoutInflater();

                View alert_view = inflater.inflate(R.layout.popup_item_space,null);

                // dialog.setView(inflater.inflate(R.layout.add_pop, null));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setView(alert_view);

                final EditText nameEdt = (EditText)alert_view.findViewById(R.id.edt_spaceitem);
                final EditText expdateEdt = (EditText)alert_view.findViewById(R.id.edt_expdate);
                final ImageView itemImg = (ImageView)alert_view.findViewById(R.id.img_spaceitem);

                pos = position;
                SpaceItem item = spaceItemList.get(pos);
                nameEdt.setText(item.getItem());
                expdateEdt.setText(item.getExpDate());
                DownloadImageTask dlTask = (DownloadImageTask) new DownloadImageTask(itemImg).execute(item.getImgUrl());


                final AlertDialog dialog = builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                SpaceItem item = spaceItemList.get(pos);
                                item.setItem(nameEdt.getText().toString().trim());
                                item.setExpDate(expdateEdt.getText().toString().trim());
                                spaceItemList.set(pos, item);
                                mListView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton(getString(R.string.cancel),new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }).create();

                dialog.show();//show the dialog

            }
        });*/


        // TODO
        // Set alarm listener
        alarmBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View view) {
                LayoutInflater inflater = getActivity().getLayoutInflater();

                View alert_view = inflater.inflate(R.layout.popup_alarm,null);

                // dialog.setView(inflater.inflate(R.layout.add_pop, null));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(alert_view);


                final UserSettings userSettings = UserSettings.getInstance(getContext());

                final Switch switch_on = (Switch) alert_view.findViewById(R.id.alarmOn);
                final Switch switch_vib = (Switch) alert_view.findViewById(R.id.alarmVibrate);
                switch_on.setChecked(userSettings.getAlarmMode());
                switch_vib.setChecked(userSettings.getVibrationMode());

                final AlertDialog dialog = builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                userSettings.setAlarmMode(switch_on.isChecked());
                                userSettings.setVibrationMode(switch_vib.isChecked());
                            }
                        }).setNegativeButton(getString(R.string.cancel),new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        }).create();

                dialog.show();//show the dialog
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
            try {
                if(this.api_url.indexOf("/get-ingredient")!=-1 && response.equals("[]")){
                    resultTxt.setText("Your fridge is empty now.");
                }
                else {
                    rendarView(response);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
        adapter = new SpaceItemAdapter(this.getContext(), spaceItemList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // Get the layout inflater
                LayoutInflater inflater = getActivity().getLayoutInflater();

                final View alert_view = inflater.inflate(R.layout.popup_item_space,null);

                // dialog.setView(inflater.inflate(R.layout.add_pop, null));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setView(alert_view);

                EditText nameEdt = (EditText)alert_view.findViewById(R.id.edt_spaceitem);
                EditText expdateEdt = (EditText)alert_view.findViewById(R.id.edt_expdate);
                ImageView itemImg = (ImageView)alert_view.findViewById(R.id.img_spaceitem);

                pos = position;
                SpaceItem item = spaceItemList.get(pos);
                nameEdt.setText(item.getItem());
                expdateEdt.setText(item.getExpDate());
                DownloadImageTask dlTask = (DownloadImageTask) new DownloadImageTask(itemImg).execute(item.getImgUrl());

                final AlertDialog dialog = builder.setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                SpaceItem item = spaceItemList.get(pos);
                                item.setItem(((EditText)alert_view.findViewById(R.id.edt_spaceitem)).getText().toString().trim());
                                item.setExpDate(((EditText)alert_view.findViewById(R.id.edt_expdate)).getText().toString().trim());

                                new RequestTask("/modify-ingredient").execute("{\"iid\":"+item.getId()+""+ "," +
                                                                                "\"modifyname\":\""+item.getItem()+"\""+ "," +
                                                                                "\"exp_date\":\""+item.getExpDate()+"\""+ "}");

                                spaceItemList.set(pos, item);
                                mListView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton(getString(R.string.cancel),new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }).create();

                dialog.show();//show the dialog

            }
        });
    }

}
