package com.nemerald.apiproject.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.nemerald.apiproject.Objects.FileSaveAndGet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFileToStorage {

    FileSaveAndGet fileSaveAndGet;
    Context context;
    String LOG_TAG = "Error in File saver instance: ";

    public SaveFileToStorage(FileSaveAndGet fileSaveAndGet, Bitmap bitmap, Context context){
        this.fileSaveAndGet = fileSaveAndGet;
        this.context = context;
        savePictureFileToStorage(bitmap);
    }
    public boolean savePictureFileToStorage(Bitmap bitmap){
        FileOutputStream fos = null;
        try {
            fos = getPictureOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
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
}
