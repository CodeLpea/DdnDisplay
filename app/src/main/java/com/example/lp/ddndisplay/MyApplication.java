package com.example.lp.ddndisplay;

import android.app.Application;

public class MyApplication extends Application {
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