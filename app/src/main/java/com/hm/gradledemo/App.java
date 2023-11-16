package com.hm.gradledemo;

import android.app.Application;

/**
 * Created by dumingwei on 2017/5/17.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //SdkManager.init(this);
        new LaunchTest().test();

//        File[] files = new File[];
//        for (File file : files) {
//
//        }

    }

}
