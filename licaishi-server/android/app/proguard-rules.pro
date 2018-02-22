# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes *JavascriptInterface*
-keepattributes InnerClasses
-keepattributes EnclosingMethod

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#忽略警告 也可以用-ignorewarnings
-dontwarn
#避免混淆泛型
#不混淆注释
-keepattributes *Annotation

#声明第三方jar包,不用管第三方jar包中的.so文件(如果有)

-keepattributes *Annotation*
-keep class * extends java.lang.annotation.Annotation { *; }


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keepclasseswithmembernames class * {
native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);
public void onReceiveTransaction(java.lang.Object[]);
}
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
public void onReceiveTransaction(java.lang.Object[]);
}
-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

-keep public class * implements java.io.Serializable {
    *;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
   public void onReceiveTransaction(java.lang.Object[]);
}

-keep public class [com.baimi.wallet].R$*{
public static final int *;
}


-dontwarn android.support.v4.**
-keep class android.support.v4.** {*;}

#-ignorewarnings

#==================JsBridge======================
-keep class com.auts.lcssv.jsbridge.** { *; }

#==================bean======================
-keep class com.auts.lcssv.bean.** { *; }

#==================butterknife======================
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

#==================okhttp==========================
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

-dontwarn okio.**
-keep class okio.**{*;}

#==================fastjson==========================
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

#==================glide==========================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#==================ument======================
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class [com.auts.lcssv].R$*{
    public static final int *;
}
-keep class com.umeng.commonsdk.** {*;}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#==================umeng push=================
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**

-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

-keep public class **.R$*{
   public static final int *;
}

#==================razor======================
-keep class com.wbtech.ums.** {*;}

#==================bugly======================
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

#==================zxing======================
-dontwarn com.google.zxing.**
-keep class com.google.zxing.**{*;}

################### region for xUtils
-keepattributes Signature,*Annotation*
-keep public class org.xutils.** {
    public protected *;
}
-keep public interface org.xutils.** {
    public protected *;
}
-keepclassmembers class * extends org.xutils.** {
    public protected *;
}
-keepclassmembers @org.xutils.db.annotation.* class * {*;}
-keepclassmembers @org.xutils.http.annotation.* class * {*;}
-keepclassmembers class * {
    @org.xutils.view.annotation.Event <methods>;
}
#################### end region

#==================XLog======================
-dontwarn com.tencent.mars.**
-keep class com.tencent.mars.**{
    *;
}

#==================X5WebView======================
-dontwarn com.tencent.smtt.**
-keep class com.tencent.smtt.**{
    *;
}
#==================BaseRecyclerViewAdapterHelper=================
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers public class * extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(android.view.View);
}

#=================================TakePhoto
-keep class com.jph.takephoto.** { *; }
-dontwarn com.jph.takephoto.**

-keep class com.darsh.multipleimageselect.** { *; }
-dontwarn com.darsh.multipleimageselect.**

-keep class com.soundcloud.android.crop.** { *; }
-dontwarn com.soundcloud.android.crop.**
#================================LuBan
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

#=================================LitePal
-keep class org.litepal.** {
    *;
}
-keep class * extends org.litepal.crud.DataSupport {
    *;
}

-keep class sun.misc. { *; }
-dontwarn sun.misc.**

#==============================EventBus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}

#==================mqtt======================
-dontwarn org.eclipse.paho.android.sample.**
-keep class org.eclipse.paho.android.sample.**{
    *;
}

-dontwarn org.eclipse.jetty.**
-keep class org.eclipse.jetty.**{
    *;
}

-dontwarn org.slf4j.**
-keep class org.slf4j.**{
    *;
}

-keep class com.taobao.** {*;}
-keep class com.alibaba.** {*;}
-dontwarn com.taobao.**
-dontwarn com.alibaba.**
-keep class com.ut.** {*;}
-dontwarn com.ut.**
-keep class com.ta.** {*;}
-dontwarn com.ta.**
#极光推送
-dontoptimize
-dontpreverify

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }

#==================viewhodler======================
-keep class com.auts.lcssv.adapter.holder.** { *; }