package com.example.lp.ddndisplay;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

public class MyApplication extends Application {
    private static final String TAG="MyApplication";
    private static MyApplication instance;
    public static MyApplication getInstance(){
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}
