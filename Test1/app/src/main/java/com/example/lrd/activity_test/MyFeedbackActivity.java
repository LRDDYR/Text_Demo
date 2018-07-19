package com.example.lrd.activity_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.lrd.R;
import com.orhanobut.logger.Logger;
import com.webianks.easy_feedback.EasyFeedback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lrd on 2018/2/8.
 */

public class MyFeedbackActivity extends AppCompatActivity {
    @BindView(R.id.feedback)
    Button feedback;
    @BindView(R.id.pattern_lock_view)
    PatternLockView mLockView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_feedback_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Logger.d("开始");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String s = PatternLockUtils.patternToString(mLockView, pattern);
                Logger.d(s);
                mLockView.clearPattern();
            }

            @Override
            public void onCleared() {
                Logger.d("onCleared");
            }
        });
    }

    @OnClick(R.id.feedback)
    public void onViewClicked() {
        new EasyFeedback.Builder(this)
                .withEmail("17610889895@163.com")
                .withSystemInfo()
                .build()
                .start();
    }
}
