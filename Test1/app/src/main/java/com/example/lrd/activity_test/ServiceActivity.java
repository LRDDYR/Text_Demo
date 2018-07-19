package com.example.lrd.activity_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lrd.R;
import com.example.lrd.activity.BaseActivity;
import com.example.lrd.hotel.view.ToolbarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created By LRD
 * on 2018/7/11
 */
public class ServiceActivity extends BaseActivity {
    @BindView(R.id.service_start)
    Button serviceStart;
    @BindView(R.id.service_stop)
    Button serviceStop;

    @Override
    protected int getContentViewId() {
        return R.layout.service_layout;
    }

    @Override
    protected boolean isShowStatusBarHeight() {
        return false;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("Service");
        toolbarHelper.setOnNavigationClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.service_start, R.id.service_stop})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, DownloadSignatureService.class);
        switch (view.getId()) {
            case R.id.service_start:
                startService(intent);
                break;
            case R.id.service_stop:
                stopService(intent);
                break;
        }
    }
}
