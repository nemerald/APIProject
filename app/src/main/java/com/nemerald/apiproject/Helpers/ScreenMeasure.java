package com.nemerald.apiproject.Helpers;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class ScreenMeasure {

    public int getScreenWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        return size.x;
    }
    public int getScreenHeight(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        return size.y;
    }
}
