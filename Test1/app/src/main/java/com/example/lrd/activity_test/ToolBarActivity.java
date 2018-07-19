package com.example.lrd.activity_test;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.lrd.R;
import com.example.lrd.activity.BaseActivity;
import com.example.lrd.hotel.view.ToolbarHelper;
import com.example.lrd.utils.StatusBarUtil;
import com.example.lrd.utils.ToastUtils;

import java.lang.reflect.Method;

/**
 * Created by lrd on 2018/5/7.
 */

public class ToolBarActivity extends BaseActivity  {

    @Override
    protected boolean isShowStatusBarHeight() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.test_tools_layout;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
//        Toolbar toolbar = toolbarHelper.getToolbar();
//        // 显示导航按钮
//        toolbar.setNavigationIcon(R.drawable.icon_back_black);
//        toolbar.setTitle("标题");
        toolbarHelper.setTitle("测试标题");
//        toolbarHelper.setMenuTitle("设置",null);
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_action1:
                // do something
                ToastUtils.getInstanc(this).showToast("sdf");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onPreparePanel(featureId, view, menu);
    }
}
