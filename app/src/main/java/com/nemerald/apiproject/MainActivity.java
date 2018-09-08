package com.nemerald.apiproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.nemerald.apiproject.Adapters.RecyclerViewAdapter;
import com.nemerald.apiproject.Helpers.HTTPRequester;
import com.nemerald.apiproject.Helpers.JSONParser;
import com.nemerald.apiproject.Objects.Gallery;
import com.nemerald.apiproject.Objects.Picture;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static String flickrAPIKey = "976f0cf74d83fe648af1f81be2cb8acb";
    static String flickrAPISecret = "5640b7fa07f99f2d";
    static String uId = "164385274@N05";
    TextView galleryTitle;
    Gallery gallery;
    private RecyclerView recyclerView;

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        galleryTitle = findViewById(R.id.galleryTitle);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

            HTTPRequester httpRequester = new HTTPRequester();
            httpRequester.setHTTPRequesterListener(new HTTPRequester.HTTPRequesterListener() {
                @Override
                public void onDataLoaded(Object response) {
                    if(!response.getClass().isInstance(VolleyError.class)){
                        initGalleryData((JSONObject) response);
                    }
                    else{
                        Log.d(getString(R.string.error_message),((VolleyError) response).getMessage());
                    }
                }
            });
        }
    }

    private void initGalleryData(JSONObject response) {
        gallery = new Gallery(response);
        galleryTitle.setText(gallery.getGalleryTitle());
        initializeAdapter();

    }

    private void initializeAdapter() {
        RecyclerViewAdapter rvAdapter = new RecyclerViewAdapter(gallery.getPictureArrayList());
        recyclerView.setAdapter(rvAdapter);
    }

    public static Context getContext(){
        return mContext;
    }

}
