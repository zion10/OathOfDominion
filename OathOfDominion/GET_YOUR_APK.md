# ⚔️ OATH OF DOMINION — Get Your APK in 10 Minutes
## Using GitHub Actions (FREE — No Android Studio Required!)

This is the **fastest method** to get a working APK without needing
Android Studio installed locally.

---

## 🚀 METHOD 1: GitHub Actions (Recommended — 10 min, totally free)

GitHub will build your APK automatically in the cloud.

### Step 1 — Create a GitHub account (if you don't have one)
Go to https://github.com → Sign up (free)

### Step 2 — Create a new repository
1. Click the **+** icon → **New repository**
2. Name it: `oath-of-dominion`
3. Set to **Private** (keeps your API key safe)
4. Click **Create repository**

### Step 3 — Upload the project
**Option A — Drag and drop (easiest):**
1. Unzip `OathOfDominion-AndroidStudio.zip`
2. Go to your new GitHub repo page
3. Click **"uploading an existing file"**
4. Drag the entire `OathOfDominion/` folder contents into the browser
5. Click **Commit changes**

**Option B — Git command line:**
```bash
cd OathOfDominion
git init
git add .
git commit -m "Initial commit - Oath of Dominion"
git remote add origin https://github.com/YOUR_USERNAME/oath-of-dominion.git
git push -u origin main
```

### Step 4 — Watch the build
1. Go to your repo → click **Actions** tab
2. You'll see **"Build Oath of Dominion APK"** running
3. Wait ~3-5 minutes for it to complete ✅

### Step 5 — Download your APK
1. Click on the completed workflow run
2. Scroll down to **Artifacts**
3. Click **OathOfDominion-debug** to download the APK zip
4. Unzip it → `app-debug.apk` is your APK!

### Step 6 — Install on your Android phone
```bash
# Via USB (with ADB):
adb install app-debug.apk

# Or just transfer the APK file to your phone and tap it
# (Enable: Settings → Security → Install Unknown Apps)
```

---

## 🔧 METHOD 2: Android Studio (5 min if already installed)

1. Open Android Studio → **File → Open** → select `OathOfDominion/` folder
2. Wait for Gradle sync (~60 seconds)
3. If prompted about Gradle wrapper: click **"Generate Gradle Wrapper"**
4. Click **Build → Build Bundle(s) / APK(s) → Build APK(s)**
5. Click **"locate"** in the toast notification
6. APK is at: `app/build/outputs/apk/debug/app-debug.apk`

---

## 📱 METHOD 3: Command Line (if Android SDK installed)

```bash
cd OathOfDominion

# Generate wrapper
gradle wrapper --gradle-version 8.2

# Build APK
chmod +x gradlew
./gradlew assembleDebug

# APK location:
# app/build/outputs/apk/debug/app-debug.apk

# Install directly to connected phone:
./gradlew installDebug
```

---

## 🔐 Creating a Signed Release APK (for sharing/Play Store)

After getting the debug APK working, create a signed release:

### Generate a keystore (one-time setup):
```bash
keytool -genkey -v \
  -keystore oath-of-dominion.jks \
  -alias oath \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000
```

### Add signing to app/build.gradle:
```groovy
android {
    signingConfigs {
        release {
            storeFile file("../oath-of-dominion.jks")
            storePassword "YOUR_STORE_PASSWORD"
            keyAlias "oath"
            keyPassword "YOUR_KEY_PASSWORD"
        }
    }
    buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled false
        }
    }
}
```

### Build signed release:
```bash
./gradlew assembleRelease
# APK: app/build/outputs/apk/release/app-release.apk
```

---

## 🆘 Troubleshooting

| Error | Fix |
|---|---|
| `SDK location not found` | Set `ANDROID_HOME=/path/to/Android/Sdk` |
| `Gradle wrapper jar missing` | Run `gradle wrapper --gradle-version 8.2` |
| `minSdk too high` | Change `minSdk 26` to `minSdk 24` in `app/build.gradle` |
| `White screen on launch` | Check `assets/index.html` exists |
| `API calls failing` | Normal — app uses cached data as fallback |
| GitHub Actions failing | Check Actions tab logs for specific error |

---

## 📋 App Features Summary

| Feature | Status |
|---|---|
| 90 real Moki leaderboard (Season 1 W12) | ✅ Built-in |
| Live API data (leaderboard/champions/matches/contests) | ✅ Auto-fetches |
| Real Moki thumbnails from CDN | ✅ Loaded from URL |
| 4-way API status indicator | ✅ Header bar |
| Matchup Engine (7×7 class matrix) | ✅ Full |
| Contest Picks with S/A/B/C tiers | ✅ Full |
| Wallet Connect (Ronin/MetaMask) | ✅ Full |
| Team Builder with synergy analysis | ✅ Full |
| Pull-to-refresh | ✅ Native Android |
| Fire splash screen | ✅ Animated |
| Demon mascot app icon | ✅ Vector |
| Back button WebView history | ✅ Native |

---

*Oath of Dominion Guild · Grand Arena Season 1 · Built with 🔥*
