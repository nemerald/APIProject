package com.nemerald.apiproject.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.nemerald.apiproject.Objects.FileSaveAndGet;
import com.nemerald.apiproject.Objects.Picture;
import com.nemerald.apiproject.Objects.SharedPreferencesHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStorageHandler {

    private FileSaveAndGet fileSaveAndGet;
    private Context context;
    private String LOG_TAG = "Error in File saver instance: ";
    private SharedPreferencesHelper sharedPreferencesHelper;
    public FileStorageHandler(FileSaveAndGet fileSaveAndGet, Context context){
        this.fileSaveAndGet = fileSaveAndGet;
        this.context = context;
        this.sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }
    public void removePictureFromFileStorage(Picture picture){
        getSharedPreferencesHelper().removeSharedPrefs(picture.getPicId());
        File file = new File(getFileSaveAndGet().getFilePath(), getFileSaveAndGet().getFileName());
        file.delete();
    }
    public void savePictureToFileToStorage(Bitmap bitmap){
        FileOutputStream fos = null;
        try {
            fos = getPictureOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            getSharedPreferencesHelper().saveToSharedPrefs(getFileSaveAndGet());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private FileOutputStream getPictureOutputStream() throws FileNotFoundException {
        return new FileOutputStream(new File(getAlbumStorageDir(), fileSaveAndGet.getFileName()));
    }

    private File getAlbumStorageDir() {
        File fileDirectory = new File(context.getFilesDir(), fileSaveAndGet.getAlbumName());
        if (!fileDirectory.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return fileDirectory;
    }
    public Context getContext() {
        return context;
    }
    public FileSaveAndGet getFileSaveAndGet() {
        return fileSaveAndGet;
    }
    public SharedPreferencesHelper getSharedPreferencesHelper() {
        return sharedPreferencesHelper;
    }
}
