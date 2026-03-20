# ⚡ BUILD YOUR APK IN 3 STEPS

## Fastest method (5 minutes total)

### Step 1 — Open project
```
Android Studio → File → Open → select OathOfDominion folder → OK
```
Wait ~60 seconds for Gradle sync.

### Step 2 — Fix wrapper (if prompted)
Click the yellow banner: **"Generate Gradle Wrapper"**
OR in terminal inside the project:
```bash
gradle wrapper --gradle-version 8.2
```

### Step 3 — Build APK
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```
APK appears at: `app/build/outputs/apk/debug/app-debug.apk`

Click **"locate"** in the notification to find it.
Transfer to phone → tap to install (allow unknown sources).

---

## Even faster: Command line (if Android SDK in PATH)
```bash
cd OathOfDominion
gradle wrapper --gradle-version 8.2
chmod +x gradlew
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

## Direct install to plugged-in phone
```bash
./gradlew installDebug
```

---
*Minimum Android: 8.0 (API 26) · Target: Android 14 (API 34)*
