# MinimumWallpaper

## The Problem

**Q:** How do you set a solid color wallpaper on your Android device?

**A:** You set your wallpaper to a tiny 1x1 pixel image with the pixel set to that color (Black, Pink, or #FF5678). Simple, fast, and memory-friendly.

**That should work.**

It does work. Or at least **it does work on most Android devices** out there (including the Nexus devices and others that run stock Android).  The system knows how to fill the entire screen with that one pixel, giving you a full solid color wallpaper. 

However (there is always a "however"), **there are Android devices that crash immediately** after setting the 1x1 pixel image wallpaper: *Unfortunately, System UI has stopped.* And they continue to crash: they crash with 2x2 images, they crash with 3x3 images, they crash with 4x4 images. And so on, until a magic size emerges, and they no longer crash.

For example, for some devices, setting a 32x32 pixel image finally gets your wallpaper working.

## The Test App

Minimum Wallpaper has been designed to answer this question: does your Android crash after setting a 1x1 pixel image wallpaper?

But more important, Minimum Wallpaper has been designed to find the magic number: **the minimum wallpaper size that works on the device**.
