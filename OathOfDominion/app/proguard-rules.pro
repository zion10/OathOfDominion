# Keep WebView JavaScript interface methods
-keepclassmembers class com.oathofdominion.grandarena.MainActivity$AndroidBridge {
    public *;
}

# Keep all annotated JS interface methods
-keepattributes JavascriptInterface

# AndroidX WebKit
-keep class androidx.webkit.** { *; }

# Prevent stripping of activity classes
-keep class com.oathofdominion.grandarena.** { *; }

# General Android rules
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception
