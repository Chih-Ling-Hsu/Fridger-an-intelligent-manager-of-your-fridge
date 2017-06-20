package com.amazonaws.mobilehelper.auth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.amazonaws.mobilehelper.auth.user.IdentityProfile;
import com.amazonaws.mobilehelper.auth.user.ProfileRetrievalException;
import com.amazonaws.mobilehelper.util.ThreadUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by User on 2017/5/30.
 */

public class ImageDownloader {

    /** Executor service for obtaining credentials in a background thread. */
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private Bitmap image = null;

    public Bitmap getImage(){
        return image;
    }

    public void loadUserIdentityProfile(final String imageUrl, final Runnable onReloadComplete) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                loadImageFromUrl(imageUrl);
                ThreadUtils.runOnUiThread(onReloadComplete);
            }
        });
    }

    public void loadImageFromUrl(final String imageUrl) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final InputStream is = new URL(imageUrl).openStream();
                    image = BitmapFactory.decodeStream(is);
                    is.close();
                    Log.e("BITMAP", String.valueOf(image.getByteCount()));
                    if(image==null){
                        Log.e("BITMAP", "null");
                    }
                    else{
                        Log.e("BITMAP", "not null");
                    }
                } catch(Exception e) {
                    Log.e("Error", e.getMessage());
                    Log.e("Error", e.toString());
                    //e.printStackTrace();
                }
                return;
            }
        });
    }
}
