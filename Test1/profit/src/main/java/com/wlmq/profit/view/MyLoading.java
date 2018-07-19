package com.wlmq.profit.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;
import com.wlmq.profit.R;

/**
 * Created by lrd on 2017/12/13.
 * Loading
 */

public class MyLoading extends Dialog{

    public MyLoading(Context context) {
        super(context, R.style.Theme_DialogProfitTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout_profit);
        AVLoadingIndicatorView LView = (AVLoadingIndicatorView)findViewById(R.id.loading_view);
        LView.setIndicatorColor(getContext().getResources().getColor(R.color.purple_profit));
        setCanceledOnTouchOutside(false);
//        setCancelable(false);
    }
}
