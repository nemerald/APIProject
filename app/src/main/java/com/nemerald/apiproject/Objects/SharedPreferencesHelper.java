package com.nemerald.apiproject.Objects;

import android.content.Context;
import android.content.SharedPreferences;

import com.nemerald.apiproject.R;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SharedPreferencesHelper implements ISharedPreferencesHelper{

    SharedPreferences.Editor editor;
    Context context;

    public SharedPreferencesHelper(Context context){
        this.context = context;
    }
    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(getSharedPrefsKey(), Context.MODE_PRIVATE);
    }
    public void saveToSharedPrefs(ArrayList<Picture> pictureArrayList){
        editor = getSharedPreferencesEditor();
        editor.putString(getSharedFavoriteGalleryKey(),parseListToJSON(pictureArrayList));
        editor.commit();
    }
    private String parseListToJSON(ArrayList<Picture> pictureArrayList) {
        JSONObject pictureObj = new JSONObject();

        return pictureObj.toString();
    }
    private SharedPreferences.Editor getSharedPreferencesEditor(){
        return getSharedPreferences().edit();
    }
    private String getSharedPrefsKey(){
        return SHARED_PREFS_KEY;
    }
    private String getSharedFavoriteGalleryKey(){
        return SAVED_FAVORITE_GALLERY_KEY;
    }
}
