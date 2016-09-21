/*
 * Minimum Wallpaper
 * Copyright (C) 2016 A Hello World Dev
 * http://www.AHelloWorldDev.com
 */
package com.ahelloworlddev.minimumwallpaper.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.ahelloworlddev.minimumwallpaper.helpers.MinimumWallpaperHelper;

/**
 * Service that sets an image with the specified size and filled with the specified color as the wallpaper.
 */
public class MinimumWallpaperService extends IntentService {

    /**
     * Extra data passed to the IntentService.
     */
    private static final String EXTRA_COLOR = "com.ahelloworlddev.minimumwallpaper.extra.COLOR";
    private static final String EXTRA_WIDTH = "com.ahelloworlddev.minimumwallpaper.extra.WIDTH";
    private static final String EXTRA_HEIGHT = "com.ahelloworlddev.minimumwallpaper.extra.HEIGHT";
    private static final String EXTRA_WHICH_SYSTEM = "com.ahelloworlddev.minimumwallpaper.extra.WHICH_SYSTEM";
    private static final String EXTRA_WHICH_LOCK = "com.ahelloworlddev.minimumwallpaper.extra.WHICH_LOCK";

    /**
     * Default values to use if no valid values were sent to the IntentService.
     */
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_COLOR = Color.BLACK;

    /**
     * Creates the LoneColorWallpaperService IntentService.
     */
    @SuppressWarnings("WeakerAccess")
    public MinimumWallpaperService() {
        super("MinimumWallpaperService");
    }

    /**
     * Starts the MinimumWallpaperService to change the current system and/or lock wallpaper to a bitmap
     * filled with the specified solid color. If the service is already performing a task this
     * action will be queued.
     */
    public static void set(Context context, int color, int width, int height, boolean whichSystem, boolean whichLock) {
        final Intent intent = new Intent(context, MinimumWallpaperService.class);
        intent.putExtra(EXTRA_COLOR, color);
        intent.putExtra(EXTRA_WIDTH, width);
        intent.putExtra(EXTRA_HEIGHT, height);
        intent.putExtra(EXTRA_WHICH_SYSTEM, whichSystem);
        intent.putExtra(EXTRA_WHICH_LOCK, whichLock);
        context.startService(intent);
    }

    /**
     * Do the actual work on the worker thread: create, fill and set the color wallpaper.
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        // Get the desired color from the intent
        final int color = intent.getIntExtra(EXTRA_COLOR, DEFAULT_COLOR);
        final int width = intent.getIntExtra(EXTRA_WIDTH, DEFAULT_WIDTH);
        final int height = intent.getIntExtra(EXTRA_HEIGHT, DEFAULT_HEIGHT);
        final boolean whichSystem = intent.getBooleanExtra(EXTRA_WHICH_SYSTEM, true);
        final boolean whichLock = intent.getBooleanExtra(EXTRA_WHICH_LOCK, true);

        MinimumWallpaperHelper.set(this, color, width, height, whichSystem, whichLock);
    }
}
