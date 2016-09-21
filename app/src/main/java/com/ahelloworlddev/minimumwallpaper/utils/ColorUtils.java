/*
 * Minimum Wallpaper
 * Copyright (C) 2016 A Hello World Dev
 * http://www.AHelloWorldDev.com
 */
package com.ahelloworlddev.minimumwallpaper.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

/**
 * Color utility methods.
 */
public class ColorUtils {

    /**
     * Useful color related constants.
     */
    private static final int MAX_RGB = 256;
    private static final String COLOR_HEX_FORMAT = "#%06X";
    private static final int NO_ALPHA_MASK = 0xFFFFFF;

    /**
     * A static random number generator for creating random colors.
     */
    private static final Random sRandom = new Random();


    /**
     * Creates and returns a random integer color value.
     */
    public static int getRandomColor() {
        return Color.rgb(sRandom.nextInt(MAX_RGB), sRandom.nextInt(MAX_RGB), sRandom.nextInt(MAX_RGB));
    }

    /**
     * Returns a color string made up of the color hex code.
     */
    public static String getColorCode(int color) {
        return String.format(COLOR_HEX_FORMAT, (NO_ALPHA_MASK & color));
    }

    /**
     * Gets the Black or White contrast color for a specified color.
     */
    public static int getContrastColor(int color) {
        // Using the perceived luminance formula: (0.299*red + 0.587*green + 0.114*blue)
        long y = (299L * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000;
        return y >= 192 ? Color.BLACK : Color.WHITE; // 192 is our opinionated value
    }

    /**
     * Returns a mutable bitmap of a specified size, filled with the specified color.
     */
    public static Bitmap createColorSwatchBitmap(int width, int height, int color) {
        final Bitmap colorBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        colorBitmap.eraseColor(color);
        return colorBitmap;
    }
}
