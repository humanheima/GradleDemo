package com.hm.gradledemo.util;

import android.util.Log;

import com.hm.gradledemo.BuildConfig;

/**
 * Created by dumingwei on 2017/3/17.
 */
public class Lg {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void e(String tag, String msg) {
        if (DEBUG){
            Log.e(tag, msg);
        }
    }
}
