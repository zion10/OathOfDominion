#!/bin/bash
# ═══════════════════════════════════════════════════════
# OATH OF DOMINION — One-click APK builder
# Run this from the OathOfDominion/ project root
# Requires: Android Studio installed with SDK
# ═══════════════════════════════════════════════════════

echo "⚔️  Oath of Dominion APK Builder"
echo "════════════════════════════════"

# Step 1: Generate Gradle wrapper if missing
if [ ! -f "gradlew" ]; then
  echo "→ Generating Gradle wrapper..."
  gradle wrapper --gradle-version 8.2 2>/dev/null || {
    echo "⚠  gradle not in PATH. Please open this project in Android Studio"
    echo "   and click 'Generate Gradle Wrapper' from the notification."
    exit 1
  }
fi

chmod +x gradlew

# Step 2: Build debug APK
echo "→ Building debug APK..."
./gradlew assembleDebug --stacktrace 2>&1

if [ $? -eq 0 ]; then
  APK=$(find . -name "*.apk" -path "*/debug/*" | head -1)
  echo ""
  echo "✅ SUCCESS! APK built at:"
  echo "   $APK"
  echo ""
  echo "Install on connected device:"
  echo "   adb install $APK"
else
  echo ""
  echo "❌ Build failed. Common fixes:"
  echo "   1. Set ANDROID_HOME: export ANDROID_HOME=~/Android/Sdk"
  echo "   2. Open in Android Studio → let it auto-fix SDK issues"
  echo "   3. Run: ./gradlew assembleDebug --info for full logs"
fi
