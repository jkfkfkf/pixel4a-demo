Pixel Demo Android Studio project

How to use:
1. Open this folder in Android Studio (Open an existing Android Studio project).
2. Let Gradle sync. (If the Gradle wrapper isn't present, use Android Studio's recommended Gradle settings.)
3. Build -> Make Project.
4. Connect Pixel 4a with USB debugging enabled.
5. Build -> Build APK(s) -> Locate app-debug.apk in app/build/outputs/apk/debug/
6. Install with `adb install -r app-debug.apk` or Run from Android Studio.

Notes:
- These images are placeholders (1080x1920). Replace files in app/src/main/res/drawable/ with your real assets.
- The project intentionally keeps dependencies minimal.

