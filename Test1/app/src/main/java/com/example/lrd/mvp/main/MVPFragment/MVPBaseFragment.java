package com.example.lrd.mvp.main.MVPFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lrd.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lrd on 2018/2/1.
 */

public abstract class MVPBaseFragment extends Fragment {
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayout(), null);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        initData();
        initListener();
        return rootView;
    }

    /**
     * 获取布局
     */
    public abstract int getLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();
    /**
     * 初始化数据
     */
    protected abstract void initListener();
    /**
     * 监听事件
     */
    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
