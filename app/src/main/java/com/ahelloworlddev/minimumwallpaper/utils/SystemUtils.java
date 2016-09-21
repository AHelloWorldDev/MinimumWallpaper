/*
 * Minimum Wallpaper
 * Copyright (C) 2016 A Hello World Dev
 * http://www.AHelloWorldDev.com
 */
package com.ahelloworlddev.minimumwallpaper.utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * System utility methods.
 */
public class SystemUtils {

    /**
     * Returns the desired minimum width and height for the wallpaper.
     */
    public static Point getWallpaperDesiredMinimumSize(Context context) {

        // Get the Wallpaper Manager
        final WallpaperManager wpManager = WallpaperManager.getInstance(context);

        // Ask the Wallpaper Manager for the desired minimum width and height for the wallpaper
        return new Point(wpManager.getDesiredMinimumWidth(), wpManager.getDesiredMinimumHeight());
    }

    /**
     * Returns the width and height of the default display.
     */
    public static Point getDefaultDisplaySize(Context context) {
        Point size = new Point();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        //windowManager.getDefaultDisplay().getSize(size);
        windowManager.getDefaultDisplay().getRealSize(size);
        return size;
    }

    /**
     * Goes home (to the Android Home Screen).
     */
    public static void goHome(Context context) {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(startMain);
    }

    /**
     * Starts an activity to view an url.
     */
    @SuppressWarnings("SameParameterValue")
    public static void viewUrl(Context context, int urlResId, int errorTextResId) {
        String url = context.getString(urlResId);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), context.getString(errorTextResId, url), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /**
     * Always show the overflow menu even if the phone has a menu button.
     *
     * http://blog.vogella.com/2013/08/06/android-always-show-the-overflow-menu-even-if-the-phone-as-a-menu/
     */
    public static void setOverflowMenuAlwaysOn(Context context) {
        try {
            ViewConfiguration config = ViewConfiguration.get(context);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }
}
