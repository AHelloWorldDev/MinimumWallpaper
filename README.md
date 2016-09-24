<a href='https://play.google.com/store/apps/details?id=com.ahelloworlddev.minimumwallpaper'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="240"/></a>

# MinimumWallpaper

## The Problem

**Q:** How do you set a black or color wallpaper on your Android device?

**A:** You use a tiny 1x1 pixel image with the pixel set to that color (Black, Pink, or #FF5678). Simple, fast, and memory-friendly.

**That should work.**

It does work. Or at least **it does work on most Android devices** (including the Nexus devices and others that run stock Android).  The system knows how to fill the entire screen with that one pixel, giving you a full solid color wallpaper. 

However (there is always a "however"), **there are Android devices that crash immediately** after setting the 1x1 pixel image wallpaper: *Unfortunately, System UI has stopped.* And they continue to crash: they crash with 2x2 images, they crash with 3x3 images, they crash with 4x4 images. And so on, until a magic size emerges, and they no longer crash.

For example, for some devices, setting a 32x32 pixel image finally gets your wallpaper working.

## The Test App

Minimum Wallpaper has been designed to answer this big question: can your Android handle setting a 1x1 pixel image wallpaper?

If not, Minimum Wallpaper can help you find the magic number: **the minimum wallpaper size that works on the device**.

##Download

- [Download from Google Play](https://play.google.com/store/apps/details?id=com.ahelloworlddev.minimumwallpaper)

- [Download APK from Github Releases](https://github.com/AHelloWorldDev/MinimumWallpaper/releases/latest)

## Screenshots

<a href="https://cloud.githubusercontent.com/assets/22292999/18807393/1dcde148-824e-11e6-852a-1bd7e65587a7.png" target="_blank">
  <img src="https://cloud.githubusercontent.com/assets/22292999/18807393/1dcde148-824e-11e6-852a-1bd7e65587a7.png" width="207"     alt="Minimum Wallpaper Screenshot - 1x1 Start"/>
</a>
<a href="https://cloud.githubusercontent.com/assets/22292999/18807394/1dd01170-824e-11e6-8589-617d4cb3d5b9.png" target="_blank">
  <img src="https://cloud.githubusercontent.com/assets/22292999/18807394/1dd01170-824e-11e6-8589-617d4cb3d5b9.png" width="207"     alt="Minimum Wallpaper Screenshot - 1x1 OK"/>
</a>
<a href="https://cloud.githubusercontent.com/assets/22292999/18807395/1dd2b4b6-824e-11e6-8516-422c11cc11f5.png" target="_blank">
  <img src="https://cloud.githubusercontent.com/assets/22292999/18807395/1dd2b4b6-824e-11e6-8516-422c11cc11f5.png" width="207"     alt="Minimum Wallpaper Screenshot - 1x1 Crash"/>
</a>

## Feedback

Please report your findings using [GitHub Issues](https://github.com/AHelloWorldDev/MinimumWallpaper/issues)

Please include some information about your device, such as manufacturer, model, and Android version. Specify if your device can handle a 1x1 pixel wallpaper. If not, please find and report the minimum wallpaper size that works on your device.

## Contributions

All contributions are welcome: from testing and [feedback](https://github.com/AHelloWorldDev/MinimumWallpaper/issues) to documentation to graphics (app icon) to code.

## License

LoneColor is licensed under the [Apache License](LICENSE).
