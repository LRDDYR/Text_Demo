package com.example.lrd.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.lrd.R;
import com.example.lrd.view.BasePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by lrd on 2017/12/21.
 * 属性动画
 */

public class ObjectAnActivity extends AppCompatActivity {
    @BindView(R.id.an_btn)
    LinearLayout anBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_an_layout);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.an_btn)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.an_btn:
                PopupWindow popupWindow = new PopupWindow(this);
                View inflate = LinearLayout.inflate(this, R.layout.test_layout, null);
                popupWindow.setContentView(inflate);
                popupWindow.showAsDropDown(view);
                break;
        }
    }
}
