package com.example.lrd.activity_test;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lrd.R;
import com.example.lrd.test.adpter.HomeListAdapter;
import com.example.lrd.test.bean.HomeItem;
import com.example.lrd.test.entity.MultipleItem;
import com.example.lrd.utils.SnackbarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lrd on 2018/1/22.
 */

public class SmartRefreshActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.test_smartRefresh)
    SmartRefreshLayout mSmartRefresh;
    @BindView(R.id.test_Tab)
    TabLayout mTestTab;
    private List<HomeItem> mData = new ArrayList();
    private List<HomeItem> mMore = new ArrayList();
    private List<MultipleItem> mMultiple = new ArrayList();
    private List<MultipleItem> mMultipleRight = new ArrayList();
    private HomeListAdapter mAdapter;
    private int statusBarHeight;
    private TabLayout mTabTl;
    private boolean isVisible = false;//顶部Tab是否可见
    private int mLastOffset;
    private int mLastPosition;
    private boolean isLeft = true;
    private int mLastOffset_right;
    private int mLastPosition_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_refresh_layout);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //状态栏高度
        statusBarHeight = frame.top;
    }

    private void initView() {
        for (int i = 0; i < 20; i++) {
            MultipleItem multipleItem1 = new MultipleItem(1);
            multipleItem1.setName("left"+i);
            mMultiple.add(multipleItem1);
        }
        for (int i = 0; i < 10; i++) {
            MultipleItem multipleItem2 = new MultipleItem(2);
            multipleItem2.setPassword("right"+i);
            mMultipleRight.add(multipleItem2);
        }
        //TabLayout
        initTabLayout();
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //创建并设置Adapter
//        mAdapter = new HomeAdapter(R.layout.test_item_brva_layout,mData);
        mAdapter = new HomeListAdapter(mMultiple);
        //添加头部
        mAdapter.addHeaderView(getView());
        mRecyclerView.setAdapter(mAdapter);


        for (int i = 0; i < 10; i++) {
            HomeItem homeItem = new HomeItem();
            homeItem.setName("BRVA" + i);
            mData.add(homeItem);
        }

    }

    private void initListener() {
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mData.clear();
                mMore.clear();
                for (int i = 0; i < 10; i++) {
                    HomeItem homeItem = new HomeItem();
                    homeItem.setName("NEW" + i);
                    mData.add(homeItem);
                }
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(2000, false);//传入false表示刷新失败
            }
        });
        mSmartRefresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                for (int i = 0; i < 5; i++) {
                    HomeItem homeItem = new HomeItem();
                    homeItem.setName("ADD" + i);
                    mMore.add(homeItem);
                }
                mData.addAll(mMore);
                mAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore(1000,true);//传入false表示加载失败
            }
        });
        mTestTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (isVisible){
                    changeList(mTabTl, tab);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mTabTl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!isVisible){
                    changeList(mTestTab,tab);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //Log.e("locationY", locationY + "   " + "topHeight的值是：" + topHeight);
                int[] location = new int[2];
                mTabTl.getLocationOnScreen(location);
                int locationY = location[1];
                if (locationY <= statusBarHeight && (mTestTab.getVisibility() == View.GONE || mTestTab.getVisibility() == View.INVISIBLE)) {
                    mTestTab.setVisibility(View.VISIBLE);
                    isVisible = true;
                }

                if (locationY > statusBarHeight && mTestTab.getVisibility() == View.VISIBLE) {
                    mTestTab.setVisibility(View.GONE);
                    isVisible = false;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
    }

    private void changeList(TabLayout Tab, TabLayout.Tab tab) {
            if ("TAB1".equals(tab.getText())) {
                Tab.getTabAt(0).select();
                isLeft = true;
                mAdapter.setNewData(mMultiple);
                SnackbarUtils.showShortSnackbar(mSmartRefresh, "TAB1", ContextCompat.getColor(SmartRefreshActivity.this, R.color.white), ContextCompat.getColor(SmartRefreshActivity.this, R.color.black));
            }else {
                isLeft = false;
                Tab.getTabAt(1).select();
                mAdapter.setNewData(mMultipleRight);
            }
            if (isVisible){
                scrollToPosition();
            }
    }

    private View getView() {
        View view = LayoutInflater.from(this).inflate(R.layout.test_header_layout, null);
        initHeader(view);
        return view;
    }

    private void initTabLayout() {
        mTestTab.setTabMode(TabLayout.MODE_FIXED);
        mTestTab.setTabTextColors(ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.grey));
        mTestTab.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.black));
        //ViewCompat.setElevation(mTabTl, 10);
        //mTabTl.setupWithViewPager(mContentVp);
        mTestTab.addTab(mTestTab.newTab().setText("TAB1"));
        mTestTab.addTab(mTestTab.newTab().setText("TAB2"));

    }
    private void initHeader(final View view) {
        mTabTl = view.findViewById(R.id.testTab);
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
        mTabTl.setTabTextColors(ContextCompat.getColor(this, R.color.black), ContextCompat.getColor(this, R.color.grey));
        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.black));
        //ViewCompat.setElevation(mTabTl, 10);
        //mTabTl.setupWithViewPager(mContentVp);
        mTabTl.addTab(mTabTl.newTab().setText("TAB1"));
        mTabTl.addTab(mTabTl.newTab().setText("TAB2"));
    }
    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            if (isLeft){
                //获取与该view的顶部的偏移量
                mLastOffset = topView.getTop();
                //得到该View的数组位置
                mLastPosition = layoutManager.getPosition(topView);
            }else {
                if (!isVisible){
                    mLastOffset_right = mLastOffset;
                    mLastPosition_right = mLastPosition;
                }else {
                    mLastOffset_right = topView.getTop();
                    mLastPosition_right = layoutManager.getPosition(topView);
                }
            }
        }
    }
    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if (isLeft){
            if(mRecyclerView.getLayoutManager() != null && mLastPosition >= 0) {
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mLastPosition, mLastOffset);
            }
        }else {
            if(mRecyclerView.getLayoutManager() != null && mLastPosition_right >= 0) {
                ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(mLastPosition_right, mLastOffset_right);
            }
        }

    }
}
