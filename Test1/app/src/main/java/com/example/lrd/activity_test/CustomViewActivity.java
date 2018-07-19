package com.example.lrd.activity_test;

import android.view.View;

import com.example.lrd.R;
import com.example.lrd.activity.BaseActivity;
import com.example.lrd.hotel.view.ToolbarHelper;

/**
 * Created by lrd on 2018/5/9.
 */

public class CustomViewActivity extends BaseActivity{
    @Override
    protected int getContentViewId() {
        return R.layout.custom_view_layout;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("CustomView");
        toolbarHelper.setOnNavigationClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {

    }
}
