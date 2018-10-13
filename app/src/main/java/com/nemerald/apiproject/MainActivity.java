package com.nemerald.apiproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.nemerald.apiproject.Objects.Cache;
import com.nemerald.apiproject.Objects.FavoriteGallery;
import com.nemerald.apiproject.Objects.SharedPreferencesHelper;
import com.nemerald.apiproject.UIHelper.BottomNavigationViewBehavior;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FragmentCommunicator{

    public static Cache mCache;
    public FavoriteGallery favoriteGallery;
    private final String LOG_TAG = "MainActivity";

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null){
            toolbar.setTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        updateToolbarText(getResources().getString(R.string.gallery_title));

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        SharedPreferences sharedPreferences = new SharedPreferencesHelper(this).getSharedPreferences();

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.rootLayout, new GalleryFragment()).commit();
            mCache = new Cache();
            favoriteGallery = new FavoriteGallery();
            favoriteGallery.setGalleryTitle(getString(R.string.favorite_gallery));
            if(sharedPreferences!=null){
                getPicturesForFavoriteGallery(sharedPreferences.getAll(), favoriteGallery);
            }
        }



    }
    private void getPicturesForFavoriteGallery(Map<String, ?> all, FavoriteGallery favoriteGallery) {
        for(Map.Entry<String,?> entry : all.entrySet()){
            try {
                favoriteGallery.addFavoritePictureToGallery(new JSONObject(entry.getValue().toString()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public static Cache getCache() { return mCache;}

    @Override
    public FavoriteGallery fetchFavoriteGallery() {
        return favoriteGallery;
    }

    @Override
    public void saveFavoriteGallery(FavoriteGallery favoriteGallery) {
        this.favoriteGallery = favoriteGallery;
    }

    @Override
    public void updateFavoriteList() {
        if(getSupportFragmentManager().getFragments().get(0) instanceof FavoriteFragment){
            FavoriteFragment fragment = (FavoriteFragment) getSupportFragmentManager().getFragments().get(0);
            if (fragment != null) {
                fragment.updateFavoriteList();
            } else {
                Log.i(LOG_TAG, "FavoriteFragment is not initialized");
            }
        }
    }
}
