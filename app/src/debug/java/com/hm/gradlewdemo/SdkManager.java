package com.hm.gradlewdemo;

import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by dumingwei on 2017/5/17.
 */
public class SdkManager {

    public static void init(Context context) {
        Stetho.initializeWithDefaults(context);
    }
}
