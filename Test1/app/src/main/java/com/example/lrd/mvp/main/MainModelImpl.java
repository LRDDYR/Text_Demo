package com.example.lrd.mvp.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lrd.R;
import com.example.lrd.mvp.main.MVPFragment.MVPBaseFragment;
import com.example.lrd.mvp.main.MVPFragment.MVPFirstFragment;
import com.example.lrd.mvp.main.MVPFragment.MVPFourFragment;
import com.example.lrd.mvp.main.MVPFragment.MVPSecondFragment;
import com.example.lrd.mvp.main.MVPFragment.MVPThreeFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * Created by lrd on 2018/2/1.
 */

public class MainModelImpl implements MainModel {
//    Random mRandom = new Random();
    /*文本信息*/
    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    /*未选择时的icon*/
    private int[] mIconUnselectIds = {
            R.drawable.icon_home_norme, R.drawable.icon_message_norme,
            R.drawable.icon_lianxi_norme, R.drawable.icon_more_norme};
    /*选择时的icon*/
    private int[] mIconSelectIds = {
            R.drawable.icon_home_press, R.drawable.icon_message_press,
            R.drawable.icon_lianxi_press, R.drawable.icon_more_press};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @Override
    public void changeState(Context context, FragmentManager fragmentManager, final CommonTabLayout mTabLayout, final ViewPager mViewPager, OnChangeListener listener) {
        // 创建一个集合,装填Fragment
        ArrayList<MVPBaseFragment> fragments = new ArrayList<>();
        // 装填
        fragments.add(new MVPFirstFragment());
        fragments.add(new MVPSecondFragment());
        fragments.add(new MVPThreeFragment());
        fragments.add(new MVPFourFragment());
        initListener(mTabLayout,mViewPager,listener);
//        // 创建ViewPager适配器
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fragmentManager);
        myPagerAdapter.setFragments(fragments);
//
//        // 给ViewPager设置适配器
//        mViewPager.setAdapter(myPagerAdapter);

        mViewPager.setAdapter(myPagerAdapter);

        /*添加数据集*/
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);
        //显示未读红点
        //mTabLayout.showMsg(1, 100);
        mTabLayout.setMsgMargin(1, -5, 5);
        //设置未读消息红点
//        mTabLayout.showDot(2);
//        MsgView rtv_2_2 = mTabLayout.getMsgView(2);
//        if (rtv_2_2 != null) {
//            UnreadMsgUtils.setSize(rtv_2_2, dp2px(7.5f));
//        }
        //设置未读消息背景
        //mTabLayout.showMsg(3, 5);
        //mTabLayout.setMsgMargin(3, 0, 5);
//        MsgView rtv_2_3 = mTabLayout.getMsgView(3);
//        if (rtv_2_3 != null) {
//            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
//        }

        mViewPager.setCurrentItem(0);
    }

    private void initListener(final CommonTabLayout mTabLayout, final ViewPager mViewPager, final OnChangeListener listener) {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                listener.change(position);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        private List<MVPBaseFragment> mFragmentList;

        public void setFragments(ArrayList<MVPBaseFragment> fragments) {
            mFragmentList = fragments;
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = mFragmentList.get(position);

            return fragment;
        }

        @Override
        public int getCount() {

            return mFragmentList.size();
        }
    }
    public class TabEntity implements CustomTabEntity {
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }
    }
}
