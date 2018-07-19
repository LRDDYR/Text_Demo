package com.example.lrd.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayoutSpringBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabScrimHelper;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.lrd.R;
import com.example.lrd.adapter.TabFragmentPagerAdapter;
import com.example.lrd.fragment.ListFragment;
import com.example.lrd.fragment.ScrollFragment;
import com.github.mmin18.widget.RealtimeBlurView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lrd on 2017/10/27.
 */

public class SlidingActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.tabs_viewpager)
    ViewPager mTabsViewpager;
    @BindView(R.id.real_time_blur_view)
    RealtimeBlurView mRealTimeBlurView;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> titleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_layout);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        /*通过Fragment作为ViewPager的数据源*/
        fragmentList = new ArrayList<>();

        fragmentList.add(new ListFragment());
        fragmentList.add(new ScrollFragment());
        fragmentList.add(new ListFragment());
        fragmentList.add(new ListFragment());
        titleList = new ArrayList<>();
        titleList.add("第一页");
        titleList.add("第二页");
        titleList.add("第三页");
        titleList.add("第四页");
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayoutSpringBehavior springBehavior = (AppBarLayoutSpringBehavior) ((CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams()).getBehavior();
        springBehavior.setSpringOffsetCallback(new AppBarLayoutSpringBehavior.SpringOffsetCallback() {
            @Override
            public void springCallback(int offset) {
                int radius = 20 * (240 - offset > 0 ? 240 - offset : 0) / 240;
                mRealTimeBlurView.setBlurRadius(radius);
            }
        });

        mTabs.setupWithViewPager(mTabsViewpager);
        mTabsViewpager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(),fragmentList,titleList));
        TabScrimHelper tabScrimHelper = new TabScrimHelper(mTabs, mToolbarLayout);
        mAppBar.addOnOffsetChangedListener(tabScrimHelper);
    }
}
