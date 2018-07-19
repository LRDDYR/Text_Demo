package com.wlmq.profit.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.lzy.okgo.OkGo;
import com.wlmq.profit.httpHelper.NetWorkUtil;
import com.wlmq.profit.permission.ContextWrap;
import com.wlmq.profit.permission.PermissionUtil;
import com.wlmq.profit.view.MyLoading;

/**
 * Created by lrd on 2017/12/14.
 * 定义父类
 */

public class BaseActivity extends AppCompatActivity{
    public BaseActivity activity;
    private boolean isRunning = true;
    private MyLoading mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        isRunning = true;
        mLoading = new MyLoading(this);
    }

    public boolean isRunning(){
        return isRunning;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //OkGo.getInstance().cancelTag(this);
        activity = null;
        isRunning = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.getInstance().handlePermissionsResult(this,requestCode,permissions,grantResults);
    }

    public void checkPermission(String[] permissions, PermissionUtil.AsynMethodListener listener, Object... objects) {
        PermissionUtil.getInstance().checkPermission(ContextWrap.of(this),permissions,listener,objects);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //OkGo.getInstance().cancelTag(this);
        }
        return super.onKeyDown(keyCode, event);
    }
}
