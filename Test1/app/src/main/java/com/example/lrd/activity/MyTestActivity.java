package com.example.lrd.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lrd.R;

/**
 * Created by lrd on 2017/10/22.
 */

public class MyTestActivity extends AppCompatActivity {
    private int a = 1;
    public boolean b = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
    }
}

