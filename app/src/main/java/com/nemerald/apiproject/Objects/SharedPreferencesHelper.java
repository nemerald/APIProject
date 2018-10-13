package com.nemerald.apiproject.Objects;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper implements ISharedPreferencesHelper{

    SharedPreferences.Editor editor;
    Context context;

    public SharedPreferencesHelper(Context context){
        this.context = context;
    }
    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(getSharedPrefsKey(), Context.MODE_PRIVATE);
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
    public void saveToSharedPrefs(FileSaveAndGet fileSaveAndGet){
        editor = getSharedPreferencesEditor();
        editor.putString(fileSaveAndGet.getFileId(), fileSaveAndGet.getFileFullPath());
        editor.commit();
    }
    public void removeSharedPrefs(String pictureId){
        editor = getSharedPreferencesEditor();
        editor.remove(pictureId);
        editor.commit();

    }
}
