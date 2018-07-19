package com.example.lrd.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lrd.R;
import com.example.lrd.hotel.view.ToolbarHelper;
import com.example.lrd.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by lrd on 2018/1/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        setContentView(getContentViewId());
        ButterKnife.bind(this);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            // 默认不显示原生标题
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            initToolbar(new ToolbarHelper(toolbar));
        }

        initView();
    }

    protected abstract int getContentViewId();

    protected void initToolbar(ToolbarHelper toolbarHelper){};

    protected abstract void initView();

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //6.0以上系统
            Window window = getWindow();
            //是否显示状态栏高度
            if (isShowStatusBarHeight()){
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.setStatusBarColor(Color.WHITE);//顶部栏
            }else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            window.setNavigationBarColor(Color.TRANSPARENT);//底部栏
        } else {
            //6.0以下系统
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //是否显示状态栏高度
            if (isShowStatusBarHeight()){
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            boolean a = StatusBarUtil.FlymeSetStatusBarLightMode(window, true);//魅族
            boolean b = StatusBarUtil.MIUISetStatusBarLightMode(window, true);//小米
            if (a || b) {
                if (isShowStatusBarHeight()){
                    window.setStatusBarColor(Color.WHITE);//顶部栏
                }else {
                    window.setStatusBarColor(Color.TRANSPARENT);
                }
                window.setNavigationBarColor(Color.TRANSPARENT);
                int i = StatusBarUtil.StatusBarLightMode(this);
                StatusBarUtil.StatusBarLightMode(this, i);
            } else {
                if (isShowStatusBarHeight()){
                    window.setStatusBarColor(Color.WHITE);//顶部栏
                }else {
                    window.setStatusBarColor(Color.TRANSPARENT);
                }
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }


    protected boolean isShowStatusBarHeight() {
        return true;
    }
}