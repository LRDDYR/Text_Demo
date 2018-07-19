package com.wlmq.profit.myInterface;

/**
 * Created by lrd on 2017/12/13.
 * 请求回调
 */

public interface ResultCallBack {
    /**
     * 请求成功
     * */
    void onSuccess(Object response);
    /**
     * 请求异常
     * */
    void onError(Object errorMsg);
}
