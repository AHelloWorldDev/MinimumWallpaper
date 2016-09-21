/*
 * Minimum Wallpaper
 * Copyright (C) 2016 A Hello World Dev
 * http://www.AHelloWorldDev.com
 */
package com.ahelloworlddev.minimumwallpaper.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Various utility methods.
 */
public class Utils {

    /**
     * Parses and returns an integer color value.
     */
    public static int parseColor(String text) {
        int value = 0;
        try {
            value = Color.parseColor(text);
        } catch (StringIndexOutOfBoundsException | IllegalArgumentException e) {
            // ignore, we will return 0
        }
        return value;
    }

    /**
     * Parses and returns an integer size value.
     */
    public static int parseSize(String text) {
        int value = 0;
        try {
            value = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            // ignore, we will return 0
        }
        return value;
    }

    /**
     * Utility method to display a toast from an IntentService.
     */
    @SuppressWarnings("SameParameterValue")
    public static void showToastOnMain(final Context context, final String text, final int duration) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), text, duration).show();
            }
        });
    }
}
