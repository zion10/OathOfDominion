# ⚔️ Oath of Dominion — Grand Arena Companion
### Android Studio Setup Guide

---

## 📁 Project Structure

```
OathOfDominion/
├── app/
│   ├── src/main/
│   │   ├── assets/
│   │   │   └── index.html              ← The full companion app (HTML/JS/CSS)
│   │   ├── java/com/oathofdominion/grandarena/
│   │   │   ├── SplashActivity.java     ← Animated fire splash screen
│   │   │   └── MainActivity.java      ← WebView host + JS bridge
│   │   └── res/
│   │       ├── drawable/
│   │       │   ├── ic_demon_logo.xml   ← Vector demon mascot
│   │       │   ├── ic_launcher_foreground.xml
│   │       │   ├── ic_launcher_background.xml
│   │       │   └── glow_circle.xml
│   │       ├── font/
│   │       │   ├── cinzel_decorative.xml
│   │       │   └── jetbrains_mono.xml
│   │       ├── layout/
│   │       │   ├── activity_splash.xml
│   │       │   └── activity_main.xml
│   │       ├── mipmap-anydpi-v26/
│   │       │   ├── ic_launcher.xml
│   │       │   └── ic_launcher_round.xml
│   │       ├── values/
│   │       │   ├── colors.xml
│   │       │   ├── strings.xml
│   │       │   ├── themes.xml
│   │       │   └── font_certs.xml
│   │       └── xml/
│   │           └── network_security_config.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── gradle/wrapper/
│   └── gradle-wrapper.properties
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## 🚀 Step-by-Step Setup in Android Studio

### Step 1 — Open the Project
1. Open **Android Studio** (Hedgehog 2023.1.1 or newer recommended)
2. Click **File → Open**
3. Navigate to the `OathOfDominion/` folder and click **OK**
4. Wait for Gradle sync to complete (first sync downloads dependencies)

---

### Step 2 — Add Missing Gradle Wrapper Files
Android Studio needs the Gradle wrapper binaries. Do ONE of these:

**Option A — Let Android Studio fix it automatically:**
- When you open the project, Android Studio will detect missing wrapper files
- Click **"Generate Gradle Wrapper"** in the notification bar

**Option B — From terminal inside the project root:**
```bash
gradle wrapper --gradle-version 8.2
```

**Option C — Copy from another project:**
Copy `gradlew`, `gradlew.bat`, and `gradle/wrapper/gradle-wrapper.jar`
from any other Android project on your machine into this folder.

---

### Step 3 — Sync & Build
1. Click **File → Sync Project with Gradle Files**
2. Wait for the sync bar at the bottom to finish
3. If there are any SDK version errors:
   - Go to **File → Project Structure → SDK Location**
   - Make sure Android SDK is installed
   - In **app/build.gradle**, adjust `compileSdk` and `targetSdk` to match your installed SDK

---

### Step 4 — Add Placeholder App Icons (Required to Build)
Android Studio requires PNG icons in the mipmap folders to compile.
The adaptive icon XMLs are already set up, but you need at least placeholder PNGs.

**Easiest method:**
1. Right-click `app/src/main/res` → **New → Image Asset**
2. Set **Icon Type** = Launcher Icons (Adaptive and Legacy)
3. Set **Foreground Layer** = your demon SVG or any image
4. Set **Background Layer** = Color → `#1A0800`
5. Click **Next → Finish**

This auto-generates all mipmap PNG sizes.

---

### Step 5 — Run on Device or Emulator

**Physical device:**
1. Enable **Developer Options** on your Android phone
2. Enable **USB Debugging**
3. Connect via USB
4. Select your device in the toolbar
5. Click ▶ **Run**

**Emulator:**
1. Click **Device Manager** (right side panel)
2. Create a new Virtual Device (Pixel 6 / API 34 recommended)
3. Select it and click ▶ **Run**

---

## ⚙️ What the App Does

| Feature | How it works |
|---|---|
| **HTML App** | Loaded from `assets/index.html` into a full-screen WebView |
| **API Calls** | Made client-side by the JS — your API key is already embedded |
| **Wallet Connect** | Calls `window.ethereum` if MetaMask/Ronin is installed, or uses demo mode |
| **JS Bridge** | `window.AndroidBridge.showToast()`, `.shareText()`, `.getApiKey()` |
| **Pull to Refresh** | SwipeRefreshLayout reloads the WebView |
| **Back Button** | Navigates WebView history before exiting |
| **Splash Screen** | Animated fire splash for 2.5 seconds |
| **Network Security** | HTTPS-only, configured for grandarena.gg domains |

---

## 🔧 Common Issues & Fixes

### "SDK location not found"
Go to **File → Project Structure → SDK Location** and point it to your Android SDK path.

### "Minimum SDK version too high"
In `app/build.gradle`, change `minSdk 26` to `minSdk 24` if you need Android 7 support.

### "Resource not found: @font/cinzel_decorative"
The fonts use Google Fonts downloadable fonts. If the emulator doesn't have Google Play Services, the fonts will fall back to system defaults. The app still works perfectly — only the splash screen text font is affected (the WebView loads its own fonts from Google Fonts CDN).

### "CLEARTEXT communication not permitted"
Already handled. The `network_security_config.xml` is set to HTTPS-only for all grandarena domains.

### App shows blank white screen
Make sure `hardwareAccelerated="true"` is in the `<application>` tag of AndroidManifest.xml (it already is). Also check that `index.html` exists in `app/src/main/assets/`.

---

## 📱 Minimum Requirements

| Property | Value |
|---|---|
| **Min Android** | API 26 (Android 8.0 Oreo) |
| **Target Android** | API 34 (Android 14) |
| **Architecture** | arm64-v8a, x86_64 |
| **Internet** | Required for API + CDN images |

---

## 🏗️ Building a Release APK

1. Go to **Build → Generate Signed Bundle / APK**
2. Choose **APK**
3. Create or choose a **keystore** (save it somewhere safe!)
4. Fill in alias + password
5. Choose **release** build variant
6. Click **Finish**

The APK will be at:
`app/release/app-release.apk`

---

## 📦 Optional: Convert to Android App Bundle (AAB) for Play Store

1. **Build → Generate Signed Bundle / APK → Android App Bundle**
2. Follow the same signing steps
3. Upload the `.aab` file to the Play Store

---

*Built for Oath of Dominion Guild — Grand Arena Season 1*
