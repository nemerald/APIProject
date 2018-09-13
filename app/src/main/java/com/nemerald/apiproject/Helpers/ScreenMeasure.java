package com.nemerald.apiproject.Helpers;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class ScreenMeasure {

    public static int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;

        return screenWidth;
    }
    public static int getScreenHeight(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;

        return screenHeight;
    }
}
