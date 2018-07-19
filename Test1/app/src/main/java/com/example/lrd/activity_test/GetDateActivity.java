package com.example.lrd.activity_test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrd.R;
import com.maning.calendarlibrary.MNCalendarVertical;
import com.maning.calendarlibrary.listeners.OnCalendarRangeChooseListener;
import com.maning.calendarlibrary.model.MNCalendarVerticalConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by lrd on 2018/1/17.
 */

public class GetDateActivity extends AppCompatActivity {
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_end)
    Button btnEnd;
    @BindView(R.id.mnCalendar)
    MNCalendarVertical mnCalendar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private DateFormat sdf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_date_layout);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        //SimpleDateFormat sdf = new SimpleDateFormat();
        sdf = SimpleDateFormat.getInstance();

        /**
         *  自定义设置相关
         */
        MNCalendarVerticalConfig mnCalendarVerticalConfig = new MNCalendarVerticalConfig.Builder()
                .setMnCalendar_showWeek(true)                   //是否显示星期栏
                .setMnCalendar_showLunar(true)                  //是否显示阴历
                .setMnCalendar_colorWeek("#000000")             //星期栏的颜色
                .setMnCalendar_titleFormat("yyyy-MM")           //每个月的标题样式
                .setMnCalendar_colorTitle("#000000")            //每个月标题的颜色
                .setMnCalendar_colorSolar("#000000")            //阳历的颜色
                .setMnCalendar_colorLunar("#dcdcdc")            //阴历的颜色
                .setMnCalendar_colorBeforeToday("#dcdcdc")      //今天之前的日期的颜色
                .setMnCalendar_colorRangeBg("#A0E0BE")        //区间中间的背景颜色
                .setMnCalendar_colorRangeText("#FFFFFF")        //区间文字的颜色
                .setMnCalendar_colorStartAndEndBg("#5DC381")    //开始结束的背景颜色
                .setMnCalendar_countMonth(6)                    //显示多少月(默认6个月)
                .build();
        mnCalendar.setConfig(mnCalendarVerticalConfig);
        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(mToolbar);
    }
    private void initListener() {
        /**
         * 区间选取完成监听
         */
        mnCalendar.setOnCalendarRangeChooseListener(new OnCalendarRangeChooseListener() {
            @Override
            public void onRangeDate(Date startDate, Date endDate) {
                String startTime = sdf.format(startDate);
                String endTime = sdf.format(endDate);
                Toast.makeText(GetDateActivity.this, "开始日期:" + startTime + ",结束日期:" + endTime, Toast.LENGTH_SHORT).show();
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.action_search:
//                        Toast.makeText(GetDateActivity.this, "Search !", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.action_notifications:
//                        Toast.makeText(GetDateActivity.this, "Notificationa !", Toast.LENGTH_SHORT).show();
//                        break;
                    case R.id.action_settings:
                        Toast.makeText(GetDateActivity.this, "Settings !", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @OnClick({R.id.btn_start, R.id.btn_end})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                break;
            case R.id.btn_end:
                break;
        }
    }
}
