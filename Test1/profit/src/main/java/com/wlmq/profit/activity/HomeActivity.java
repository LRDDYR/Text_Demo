package com.wlmq.profit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wlmq.profit.R;
import com.wlmq.profit.base.BaseActivity;
import com.wlmq.profit.httpHelper.NetWorkUtil;
import com.wlmq.profit.httpHelper.Url;
import com.wlmq.profit.myInterface.ResultCallBack;
import com.wlmq.profit.utils.Constant;
import com.wlmq.profit.utils.SharedPreferencesUtils;
import com.wlmq.profit.utils.ToastUtils;
import com.wlmq.profit.view.MyLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lrd on 2017/12/14.
 * 首页
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private TextView mYesterdayProfit;
    private TextView mAllProperty;
    private TextView mAddUpProfit;
    private TextView mSevenYear;
    private TextView mThousandProfit;
    private LinearLayout mErrorPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        initView();
        initListener();
        initData();
    }
    
    private void initView() {
        RelativeLayout mStatus = (RelativeLayout) findViewById(R.id.status_bar_rl);
        TextView mTitle = (TextView) findViewById(R.id.status_center_text);
        ImageView mBackImg = (ImageView) findViewById(R.id.status_left_img);
        ImageView mRightImg = (ImageView) findViewById(R.id.status_right_img);

        mStatus.setBackgroundResource(R.color.purple_profit);
        mBackImg.setImageResource(R.drawable.ic_nav_left_default);
        mRightImg.setImageResource(R.drawable.question2x);
        mRightImg.setVisibility(View.VISIBLE);

        //昨日收益
        mYesterdayProfit = (TextView) findViewById(R.id.home_yesterdayProfit);
        //总资产
        mAllProperty = (TextView) findViewById(R.id.home_allProperty);
        //累计收益
        mAddUpProfit = (TextView) findViewById(R.id.home_addUpProfit);
        //七日年化
        mSevenYear = (TextView) findViewById(R.id.home_sevenYear);
        //万份收益
        mThousandProfit = (TextView) findViewById(R.id.home_thousandProfit);
        //我的账户
        LinearLayout mMyAccount = (LinearLayout) findViewById(R.id.home_myAccount);
        //赎回
        LinearLayout mRedeemBtn = (LinearLayout) findViewById(R.id.home_btn_redeem);
        //购买
        LinearLayout mBuyBtn = (LinearLayout) findViewById(R.id.home_btn_buy);
        //加载失败页面
        mErrorPager = (LinearLayout) findViewById(R.id.error_layout);
        //刷新
        LinearLayout mRefresh = (LinearLayout) findViewById(R.id.error_refresh_btn);

        LinearLayout mABtn = (LinearLayout) findViewById(R.id.home_addUpBtn);
        LinearLayout mSBtn = (LinearLayout) findViewById(R.id.home_sevenYearBtn);
        LinearLayout mTBtn = (LinearLayout) findViewById(R.id.home_thousandBtn);

        mBackImg.setOnClickListener(this);
        mRightImg.setOnClickListener(this);
        mRefresh.setOnClickListener(this);
        mABtn.setOnClickListener(this);
        mSBtn.setOnClickListener(this);
        mTBtn.setOnClickListener(this);
        mMyAccount.setOnClickListener(this);
        mRedeemBtn.setOnClickListener(this);
        mBuyBtn.setOnClickListener(this);
    }

    private void initListener() {}
    
    private void initData() {
        NetWorkUtil.getInstance()
                .setLoading(new MyLoading(activity))
                .setTag(this)
                .setParams(Url.HOME_DATA,null)
                .post(new ResultCallBack() {
                    @Override
                    public void onSuccess(Object response) {
                        mErrorPager.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(response.toString())){
                            SharedPreferencesUtils.saveString(activity, Constant.HOME_DATA,response.toString());
                            try {
                                JSONObject mData = new JSONObject(response.toString());
                                mYesterdayProfit.setText(mData.optString("income1")+getResources().getString(R.string.yuan));
                                mAllProperty.setText(mData.optString("avl_vol1")+getResources().getString(R.string.yuan));
                                mAddUpProfit.setText(mData.optString("fund_vol1")+getResources().getString(R.string.yuan));
                                mSevenYear.setText("("+mData.optString("yearIncomeValue1")+"%)");
                                mThousandProfit.setText(mData.optString("income_unit1")+getResources().getString(R.string.yuan));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Object errorMsg) {
                        mErrorPager.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.status_left_img){
            finish();
        }else if (id == R.id.status_right_img){
            startActivity(new Intent(activity,WebViewActivity.class));
        }else if (id == R.id.error_refresh_btn){
            initData();
        }else if (id == R.id.home_addUpBtn){
            startActivity(new Intent(activity,AddUpActivity.class));
        }else if (id == R.id.home_sevenYearBtn){

        }else if (id == R.id.home_thousandBtn){

        }else if (id == R.id.home_myAccount){

        }else if (id == R.id.home_btn_redeem){

        }else if (id == R.id.home_btn_buy){

        }
    }
}
