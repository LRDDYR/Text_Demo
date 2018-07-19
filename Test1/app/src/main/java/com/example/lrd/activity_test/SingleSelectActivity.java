package com.example.lrd.activity_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lrd.R;
import com.example.lrd.hotel.CalendarAdapter;
import com.example.lrd.hotel.bean.HotelDateBean;
import com.example.lrd.hotel.bean.HotelDayBean;
import com.example.lrd.hotel.bean.HotelMonthBean;
import com.example.lrd.hotel.helper.DateBeanHelper;
import com.example.lrd.hotel.view.DividerItemDecortion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lrd on 2018/5/7.
 */

public class SingleSelectActivity extends AppCompatActivity {
    private static final String TAG = SingleSelectActivity.class.getSimpleName();
    RecyclerView calendarRV;
    private List<HotelDateBean> items = new ArrayList<>();
    private DateBeanHelper mDateBeanHelper;
    private CalendarAdapter adapter;
    private GridLayoutManager mGridLayoutManager;
    private HotelDayBean selectBean;
    private int monthIndex=0;
    private int maxMonthPeroid=24;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period);
        calendarRV = (RecyclerView) findViewById(R.id.rv_calendar);
        mGridLayoutManager = new GridLayoutManager(this, 7);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                HotelDateBean bean = items.get(position);
                if (bean instanceof HotelMonthBean)
                    return 7;
                else return 1;
            }
        });
        calendarRV.addItemDecoration(new DividerItemDecortion(this));
        calendarRV.setLayoutManager(mGridLayoutManager);
        mDateBeanHelper = new DateBeanHelper();
        initItems();
        adapter = new CalendarAdapter(this, items);
        calendarRV.setAdapter(adapter);
        adapter.setSingleClickListener(new CalendarAdapter.OnSingleItemClickListener() {
            @Override
            public void onSingleItemClick(int position) {
                HotelDayBean bean = (HotelDayBean) items.get(position);
                if (bean == null || bean.getYear() == 0)
                    return;
                mDateBeanHelper.solveSingleClick(items, position);
                adapter.notifyDataSetChanged();
                selectBean = bean;
            }
        });

        calendarRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(monthIndex>=maxMonthPeroid)
                    return;
                if(mDateBeanHelper.isLastItemVisible(recyclerView,items.size())){
                    mDateBeanHelper.loadMoreItems(items,monthIndex);
                    monthIndex++;
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initItems() {
        HotelDayBean select = new HotelDayBean(2017, 8, 9, HotelDayBean.STATE_CHOSED);
        mDateBeanHelper.convert2CalendarFormat(select);
        for (int i = 0; i < 6; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, i);
            if (i != 0)
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            items.addAll(mDateBeanHelper.getHotelDateBeans(calendar));
            monthIndex++;
        }
        for (HotelDateBean dateBean : items) {
            if (dateBean instanceof HotelDayBean) {
                mDateBeanHelper.isSelected((HotelDayBean) dateBean, select);
            }
        }
    }
}