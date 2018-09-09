package com.nemerald.apiproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.view.MenuItem;

import com.nemerald.apiproject.Objects.Cache;

public class MainActivity extends AppCompatActivity {

    public static Context mContext;
    public static Cache mCache;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favorite:
                    getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, new FavoriteFragment()).commit();
                    updateToolbarText(item.getTitle().toString());
                    return true;
                case R.id.navigation_gallery:
                    getSupportFragmentManager().beginTransaction().replace(R.id.rootLayout, new GalleryFragment()).commit();
                    updateToolbarText(item.getTitle().toString());
                    return true;
            }
            return false;
        }
    };

    private void updateToolbarText(String title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.rootLayout, new GalleryFragment()).commit();
        updateToolbarText(getResources().getString(R.string.gallery_title));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mContext = this;
        mCache = new Cache();
    }
    public static Context getContext(){
        return mContext;
    }
    public static Cache getCache() { return mCache;}
}
