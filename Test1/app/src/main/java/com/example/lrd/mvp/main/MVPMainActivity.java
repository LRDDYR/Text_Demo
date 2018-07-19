package com.example.lrd.mvp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lrd.R;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lrd on 2018/1/31.
 */

public class MVPMainActivity extends AppCompatActivity implements MainView {
    Random mRandom = new Random();
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_main_activity);
        ButterKnife.bind(this);

        MainPresenterImpl mainPresenter = new MainPresenterImpl(this,this);
        mainPresenter.validateCredentials(getSupportFragmentManager(), mTabLayout, mViewPager);
    }

    @Override
    public void tabCheckListener(int position) {
        if (position == 1) {
            mTabLayout.showMsg(1, mRandom.nextInt(100) + 1);
//          UnreadMsgUtils.show(mTabLayout_2.getMsgView(0), mRandom.nextInt(100) + 1);
        }
    }
}
