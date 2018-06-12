package com.hm.gradledemo;

import android.app.Application;

import com.hm.gradlewdemo.SdkManager;

/**
 * Created by dumingwei on 2017/5/17.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SdkManager.init(this);

    }

}
