/*
 * Minimum Wallpaper
 * Copyright (C) 2016 A Hello World Dev
 * http://www.AHelloWorldDev.com
 */
package com.ahelloworlddev.minimumwallpaper.helpers;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.ahelloworlddev.minimumwallpaper.R;
import com.ahelloworlddev.minimumwallpaper.utils.ColorUtils;
import com.ahelloworlddev.minimumwallpaper.utils.SystemUtils;
import com.ahelloworlddev.minimumwallpaper.utils.Utils;

/**
 * This class sets an image with the specified size and filled with the specified color as the wallpaper.
 */
public class MinimumWallpaperHelper {

    private static final String TAG = "MinimumWallpaperHelper";

    /**
     * Set an image with the specified size and filled with the specified color as the wallpaper.
     */
    public static void set(Context context, int color, int width, int height, boolean whichSystem, boolean whichLock) {
        try {

            // Get the Wallpaper Manager and the desired minimum width and height for the wallpaper
            final WallpaperManager wpManager = WallpaperManager.getInstance(context);

            // Create the color swatch bitmap
            final Bitmap colorBitmap = ColorUtils.createColorSwatchBitmap(width, height, color);

            // Set the wallpaper
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                // On Android N and above use the new API to set both the general system wallpaper and
                // the lock-screen-specific wallpaper
                int which = 0;
                if (whichSystem) which |= WallpaperManager.FLAG_SYSTEM;
                if (whichLock) which |= WallpaperManager.FLAG_LOCK;
                wpManager.setBitmap(colorBitmap, null, true, which);
            } else {
                wpManager.setBitmap(colorBitmap);
            }

            // Show & log success feedback
            String message = context.getString(R.string.toast_wallpaper_success, width, height);
            Log.i(TAG, message);
            Utils.showToastOnMain(context, message, Toast.LENGTH_LONG);

            // Go to the home screen to view the new RGB Color Wallpaper
            SystemUtils.goHome(context);

        } catch (Error | Exception e) {

            // Show & log failure feedback and exception
            String message = context.getString(R.string.toast_wallpaper_fail, width, height, e.toString());
            Log.i(TAG, message, e);
            Utils.showToastOnMain(context, message, Toast.LENGTH_LONG);
        }
    }
}
