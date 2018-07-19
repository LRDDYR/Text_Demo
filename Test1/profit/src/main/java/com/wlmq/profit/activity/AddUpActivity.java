package com.wlmq.profit.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.wlmq.profit.R;
import com.wlmq.profit.adapter.AddUpAdapter;
import com.wlmq.profit.base.BaseActivity;
import com.wlmq.profit.httpHelper.NetWorkUtil;
import com.wlmq.profit.httpHelper.Url;
import com.wlmq.profit.myInterface.ResultCallBack;
import com.wlmq.profit.utils.Constant;
import com.wlmq.profit.utils.DialogUtils;
import com.wlmq.profit.utils.SharedPreferencesUtils;
import com.wlmq.profit.utils.ToastUtils;
import com.wlmq.profit.view.MyLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lrd on 2017/12/15.
 * 累计收益
 */

public class AddUpActivity extends BaseActivity implements View.OnClickListener {

    private JSONObject mHomeData;
    private JSONArray mData = new JSONArray();
    private XRecyclerView mRecyclerView;
    private AddUpAdapter mAdapter;
    private LinearLayout mErrorPager;
    private LinearLayout mLoadingPager;
    private TextView mAllProfit;
    private boolean isLoadingMore = false;
    private int mTotalPages;//总页数
    private int mCurrentPage = 1;//当前页
    private String mTokenName;//key


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_up_layout);

        initView();
        initListener();
        initData();
    }

    private void initView() {
        String homeJson = SharedPreferencesUtils.getString(activity, Constant.HOME_DATA, "");
        try {
            mHomeData = new JSONObject(homeJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ImageView mBack = (ImageView) findViewById(R.id.status_left_img);
        mAllProfit = (TextView) findViewById(R.id.add_allProfit);
        //加载页面
        mLoadingPager = (LinearLayout) findViewById(R.id.loading_layout);
        mErrorPager = (LinearLayout) findViewById(R.id.error_layout);
        LinearLayout mErrorBtn = (LinearLayout) findViewById(R.id.error_refresh_btn);
        mRecyclerView = (XRecyclerView) findViewById(R.id.add_recyclerView);

        mBack.setOnClickListener(this);
        mErrorBtn.setOnClickListener(this);

        //set  RecyclerView
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager SGL = new LinearLayoutManager(activity);
        SGL.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(SGL);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.Pacman);
        //mRecyclerView.addItemDecoration(new HorizontalDecoration(1, getResources().getDrawable(R.drawable.gray_line)));
        mRecyclerView.setItemAnimator(null);
        mAdapter = new AddUpAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mRecyclerView.refreshComplete();
                    }
                },500);
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCurrentPage = mCurrentPage + 1;
                        if (mCurrentPage >= mTotalPages){
                            getSecretKey();
                        }else {
                            //mCurrentPage = mTotalPages;
                            ToastUtils.show("没有数据了");
                        }
                        mRecyclerView.loadMoreComplete();
                    }
                },500);
            }
        });
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("fund_vol1",mHomeData.optString("fund_vol1"));
        params.put("key",mHomeData.optString("key"));

        if (!isLoadingMore){
            params.put("cust_name",mHomeData.optString("cust_name"));
            params.put("cust_type",mHomeData.optString("cust_type"));
            params.put("global_type",mHomeData.optString("global_type"));
            params.put("global_id",mHomeData.optString("global_id"));
            params.put("cellPhoneNo",mHomeData.optString("cellPhoneNo"));
            params.put("address",mHomeData.optString("address"));
            params.put("settle_acct_no",mHomeData.optString("settle_acct_no"));
            params.put("ta_code",mHomeData.optString("ta_code"));
            params.put("product_code",mHomeData.optString("product_code"));
            params.put("yearIncomeValue1",mHomeData.optString("yearIncomeValue1"));
            params.put("avl_vol1",mHomeData.optString("avl_vol1"));
            params.put("cifNo",mHomeData.optString("cifNo"));
            params.put("Tip",mHomeData.optString(""));
        }else {
            params.put("_tokenName",mTokenName);
            params.put("settle_acct_no","");
            params.put("ta_code","");
            params.put("product_code","");
            params.put("currentPage",String.valueOf(mCurrentPage));
            params.put("totalPages",String.valueOf(mTotalPages));
        }
        NetWorkUtil.getInstance().setLoading(new MyLoading(activity))
                .setTag(this)
                .setParams(Url.ADD_UP_DATA,params)
                .post(new ResultCallBack() {
                    @Override
                    public void onSuccess(Object response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            JSONArray jsonArray = jsonObject.optJSONArray("List");
                            mTotalPages = jsonObject.optInt("totalPages");
                            String fund_vol1 = jsonObject.optString("fund_vol1");
                            mAllProfit.setText(fund_vol1+"("+getResources().getString(R.string.yuan)+")");
                            if (jsonArray != null && jsonArray.length() > 0){
                                if (!isLoadingMore){
                                    mAdapter.resetData(jsonArray);
                                }else {
                                    mAdapter.addMore(jsonArray);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object errorMsg) {
                        DialogUtils.showHintDialog(activity,errorMsg.toString());
                    }
                });
    }

    private void getSecretKey() {
        NetWorkUtil.getInstance().setParams(Url.ADD_GET_KEY,null).setTag("getKey").post(new ResultCallBack() {
            @Override
            public void onSuccess(Object response) {
                mTokenName = response.toString();
                isLoadingMore = true;
                initData();
            }

            @Override
            public void onError(Object errorMsg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.status_left_img){
            finish();
        }else if (id == R.id.error_refresh_btn){
            initData();
        }

    }
}
