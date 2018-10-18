# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


## Crashlytics
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

## GMS
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**

## OkHttp3
-dontwarn okhttp3.**
-keep class okhttp3.* { *; }
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule

## OkIO
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

## Simple XML
-dontwarn javax.xml.stream.**

-keep public class org.simpleframework.** { *; }
-keep class org.simpleframework.xml.** { *; }
-keep class org.simpleframework.xml.core.** { *; }
-keep class org.simpleframework.xml.util.** { *; }

-keepattributes ElementList, Element, Root, *Annotation*, Signature

-keepclassmembers class * {
    @org.simpleframework.xml.* *;
}

-keep @org.simpleframework.xml.* class *
