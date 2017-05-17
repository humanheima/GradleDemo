package com.hm.gradledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hm.gradledemo.util.Lg;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lg.e(TAG, getString(R.string.build_time));
        Lg.e(TAG, getString(R.string.build_host));
        Lg.e(TAG, getString(R.string.build_revision));
        Logger.d("debug和release差异化打包");
    }
}
