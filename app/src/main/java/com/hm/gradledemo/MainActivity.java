package com.hm.gradledemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.hm.gradledemo.util.Lg;

/**
 * Created by p_dmweidu on 2022/11/30
 * Desc:
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private TextView tvPackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lg.e(TAG, getString(R.string.build_time));
        Lg.e(TAG, getString(R.string.build_host));
        Lg.e(TAG, getString(R.string.build_revision));
        tvPackageName = findViewById(R.id.tv_package_name);
        tvPackageName.setText("packageName:" + getPackageName());
        getApplicationContext();

        getApplication();
    }
}
