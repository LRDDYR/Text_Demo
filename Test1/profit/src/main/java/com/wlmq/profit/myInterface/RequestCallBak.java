package com.wlmq.profit.myInterface;

import android.content.Context;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.wlmq.profit.view.MyLoading;

/**
 * Created by lrd on 2017/12/15.
 */

public abstract class RequestCallBak extends AbsCallback<String> {
    private StringConvert convert;

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);

    }

    @Override
    public void onSuccess(Response<String> response) {
        convert = new StringConvert();
    }

    @Override
    public String convertResponse(okhttp3.Response response) throws Throwable {
        String s = convert.convertResponse(response);
        response.close();
        return s;
    }
}
