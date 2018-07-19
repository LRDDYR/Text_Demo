package com.example.lrd.mvp.main.MVPFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lrd.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lrd on 2018/2/1.
 */

public class MVPFourFragment extends MVPBaseFragment {
    @BindView(R.id.testText)
    TextView testText;

    @Override
    public int getLayout() {
        return R.layout.test_text_layout;
    }

    @Override
    protected void initView() {
        testText.setText("Four");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
