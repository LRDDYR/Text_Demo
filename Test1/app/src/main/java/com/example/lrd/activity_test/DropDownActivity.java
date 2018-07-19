package com.example.lrd.activity_test;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.example.lrd.R;
import com.example.lrd.activity.BaseActivity;
import com.example.lrd.hotel.view.ToolbarHelper;
import com.example.lrd.view.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created By LRD
 * on 2018/5/20
 */
public class DropDownActivity extends BaseActivity {
    @BindView(R.id.dropDownMenu)
    DropDownMenu mDownMenu;
    private String headers[] = {"AA", "BB"};
    private List<View> popupViews = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.drop_layout;
    }

    @Override
    protected void initView() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.filter_drop_layout, null);
        popupViews.add(view1);
        View view = getLayoutInflater().inflate(R.layout.custom_drop_layout, null);
        popupViews.add(view);

        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        mDownMenu.setDropDownMenu(Arrays.asList(headers),popupViews,contentView);
    }

    private void initAnimation() {

    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("DropDown");
        toolbarHelper.setOnNavigationClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
