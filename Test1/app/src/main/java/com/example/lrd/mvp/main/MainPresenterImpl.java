package com.example.lrd.mvp.main;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SegmentTabLayout;

/**
 * Created by lrd on 2018/2/1.
 */

public class MainPresenterImpl implements MainPresenter,MainModel.OnChangeListener{
    private MainView mainView;
    private MainModelImpl mainModel;
    private Context context;

    public MainPresenterImpl(Context context,MainView mainView){
        this.context = context;
        this.mainView = mainView;
        this.mainModel = new MainModelImpl();
    }
    @Override
    public void validateCredentials(FragmentManager fragmentManager, CommonTabLayout tabLayout, ViewPager viewPager) {
        mainModel.changeState(context,fragmentManager,tabLayout,viewPager,this);
    }

    @Override
    public void init() {

    }

    @Override
    public void change(int position) {
        mainView.tabCheckListener(position);
    }
}
