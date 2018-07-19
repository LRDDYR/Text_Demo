package com.wlmq.profit.httpHelper;

import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.wlmq.profit.R;
import com.wlmq.profit.activity.Profit;
import com.wlmq.profit.myInterface.ResultCallBack;
import com.wlmq.profit.utils.ToastUtils;
import com.wlmq.profit.utils.Utils;
import com.wlmq.profit.view.MyLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by lrd on 2017/12/13.
 */

public class NetWorkUtil {
    private String url;
    private Map<String,String> params;
    private Object mTag;
    private MyLoading mLoading;
    private static NetWorkUtil netWorkUtil;
    private final String msg = "请求数据失败";

    public static NetWorkUtil getInstance(){
        if (netWorkUtil == null){
            netWorkUtil = new NetWorkUtil();
        }
        return netWorkUtil;
    }

    public NetWorkUtil setParams(String url, Map<String,String> params) {
        this.url = url;
        this.params = params;
        return this;
    }
    public NetWorkUtil setTag(Object Tag){
        this.mTag = Tag;
        return this;
    }

    public NetWorkUtil setLoading(MyLoading loading) {
        mLoading = loading;
        return this;
    }

    public void post(final ResultCallBack callBack){
        if (!Utils.isNetworkAvailable()) {
            ToastUtils.show(Profit.getInstance().getContext().getString(R.string.net_state));
            return;
        }
        OkGo.<String>post(url)
                .tag(mTag)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        if (mLoading != null){
                            mLoading.show();
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        checkResponseBody(response.body(),callBack);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        callBack.onError("msg");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (mLoading != null){
                            mLoading.dismiss();
                        }
                    }
                });
    }

    //解析报文
    private void checkResponseBody(String body, ResultCallBack callBack) {
        if (!TextUtils.isEmpty(body)){
            //特殊处理 获取key
            if ("getKey".equals(mTag)){
                callBack.onSuccess(body);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(body);
                String rejCode = jsonObject.optString("_RejCode");
                if ("000000".equals(rejCode)){
                    callBack.onSuccess(body);
                } else {
                    callBack.onError(msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            callBack.onError("数据为空");
        }
    }
}
