package com.example.lrd.activity_test;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lrd.R;
import com.example.lrd.test.bean.HomeItem;
import com.example.lrd.view.ScrollViewExt;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lrd on 2018/1/19.
 */

public class ListViewActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView_left)
    RecyclerView mRecyclerViewLeft;
    @BindView(R.id.recyclerView_right)
    RecyclerView mRecyclerViewRight;
    @BindView(R.id.down_Tab)
    TabLayout mDownTab;
    @BindView(R.id.test_home_scrollView)
    ScrollViewExt mScrollView;
    @BindView(R.id.test_home_swipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    private List<HomeItem> mData = new ArrayList();
    private List<HomeItem> mMore = new ArrayList();
    private List<HomeItem> mData_Right = new ArrayList();
    private HomeAdapter mAdapter;
    private RightAdapter mRightAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_layout);
        ButterKnife.bind(this);
        mScrollView.smoothScrollTo(0,0);
        initTab();
        initVeiw();
        initListener();
    }

    private void initTab() {
        mDownTab.setTabMode(TabLayout.MODE_FIXED);
        mDownTab.setTabTextColors(ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.grey));
        mDownTab.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.black));
        //ViewCompat.setElevation(mTabTl, 10);
        //mTabTl.setupWithViewPager(mContentVp);
        mDownTab.addTab(mDownTab.newTab().setText("TAB1"));
        mDownTab.addTab(mDownTab.newTab().setText("TAB2"));
    }

    private void initVeiw() {
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewLeft.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerViewLeft.setHasFixedSize(true);
        mRecyclerViewLeft.setItemAnimator(new DefaultItemAnimator());
        //创建并设置Adapter
        mAdapter = new HomeAdapter(R.layout.test_item_brva_layout, mData);
        mRecyclerViewLeft.setAdapter(mAdapter);
        mRecyclerViewLeft.setNestedScrollingEnabled(false);

        mRecyclerViewRight.setLayoutManager(new LinearLayoutManager(this));
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerViewRight.setHasFixedSize(true);
        mRecyclerViewRight.setItemAnimator(new DefaultItemAnimator());
        //创建并设置Adapter
        mRightAdapter = new RightAdapter(R.layout.test_header_right_layout, mData_Right);
        mRecyclerViewRight.setAdapter(mRightAdapter);
        mRecyclerViewRight.setNestedScrollingEnabled(false);


        for (int i = 0; i < 10; i++) {
            HomeItem homeItem = new HomeItem();
            homeItem.setName("BRVA" + i);
            mData.add(homeItem);
            mData_Right.add(homeItem);
        }
    }

    private void initListener() {
        mDownTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //mScrollView.smoothScrollTo(0,0);
                if ("TAB1".equals(tab.getText())) {
                    mRecyclerViewLeft.setVisibility(View.VISIBLE);
                    mRecyclerViewRight.setVisibility(View.GONE);
                } else {
                    mRecyclerViewLeft.setVisibility(View.GONE);
                    mRecyclerViewRight.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (mScrollView != null) {
            mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (mSwipeRefresh != null) {
                        mSwipeRefresh.setEnabled(mScrollView.getScrollY() == 0);
                        //CommonLog.infoLog("init scrollView");
                    }
                }
            });
        }
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.clear();
                        mMore.clear();
                        for (int i = 0; i < 10; i++) {
                            HomeItem homeItem = new HomeItem();
                            homeItem.setName("NEw" + i);
                            mData.add(homeItem);
                        }
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefresh.setRefreshing(false);
                    }
                }, 1200);
            }
        });
        mScrollView.setScrollViewListener(new ScrollViewExt.IScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                for (int i = 0; i < 5; i++) {
                    HomeItem homeItem = new HomeItem();
                    homeItem.setName("ADD" + i);
                    mMore.add(homeItem);
                }
                mData.addAll(mMore);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScrolledToTop() {

            }
        });
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(
                new ViewTreeObserver.OnScrollChangedListener() {

                    @Override
                    public void onScrollChanged() {
                        // 可以先判断ScrollView中的mView是不是在屏幕中可见
                        Rect scrollBounds = new Rect();
                        mScrollView.getHitRect(scrollBounds);
                        if (!mDownTab.getLocalVisibleRect(scrollBounds)) {
                            mDownTab.setFocusableInTouchMode(true);
                            //return;
                        }else {
                            mDownTab.requestFocus();
                        }
                        // 再用封装好的工具类检查可见性是否大于50%
//                        if (VisibilityCheckUtil.check(mView)) {
//                            // do something
//                        }
                    }
                });
    }
    //单列表
    private class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {
        private HomeAdapter(@LayoutRes int layoutResId, @Nullable List<HomeItem> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeItem item) {
            helper.setText(R.id.test_item_brva_text, item.getName());
        }
    }

    //单列表-right
    private class RightAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {
        private RightAdapter(@LayoutRes int layoutResId, @Nullable List<HomeItem> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeItem item) {
            helper.setText(R.id.test_item_rightText, item.getName());
        }
    }
}
