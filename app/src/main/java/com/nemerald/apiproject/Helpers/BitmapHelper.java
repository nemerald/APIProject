package com.nemerald.apiproject.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nemerald.apiproject.Objects.Picture;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static com.nemerald.apiproject.Helpers.ScreenMeasure.getScreenWidth;
import static com.nemerald.apiproject.MainActivity.getCache;

public class BitmapHelper {

    public static Bitmap getBitmapFromURL(Picture picture) {
        try {
            java.net.URL url = new java.net.URL(picture.getPictureUrl(picture));
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            int imageWidth = myBitmap.getWidth();
            int imageHeight = myBitmap.getHeight();
            float scaleFactor = (float)getScreenWidth()/(float)imageWidth;
            int newHeight = (int)(imageHeight * scaleFactor);

            myBitmap = Bitmap.createScaledBitmap(myBitmap, getScreenWidth(), newHeight, true);

            getCache().addBitmapToMemoryCache(picture.getPicId(), myBitmap);

            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}