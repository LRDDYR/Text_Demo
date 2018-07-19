package com.wlmq.profit.utils;

import android.widget.Toast;

import com.wlmq.profit.activity.Profit;


/**
 * Created by lrd on 2017/10/27.
 * 单例的吐司
 */

public class ToastUtils {
    private static Toast toast;
    public static void show(String content) {
        if (toast == null) {
            toast = Toast.makeText(Profit.getInstance().getContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
