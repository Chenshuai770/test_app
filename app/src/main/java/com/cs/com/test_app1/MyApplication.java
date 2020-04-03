package com.cs.com.test_app1;

import android.app.Application;

public class MyApplication extends Application {
    public static Application		mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;
    }
}
