package com.wlmq.profit.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wlmq.profit.base.BaseActivity;

/**
 * Created by lrd on 2017/12/14.
 */

public class DialogUtils {
    public DialogUtils(){
    }
    public static void showAlertDialog(BaseActivity activity, String message, final onDialogClickListener listener){
        if (activity!=null&&activity.isRunning()){
            new MaterialDialog.Builder(activity).title("提示").content(message)
                    .positiveText("确认")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            listener.onPositive();
                        }
                    })
                    .negativeText("取消")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            listener.onNegative();
                        }
                    })
                    .show();
        }
    }
    public static void showCustomAlertDialog(BaseActivity activity, String title, String message, String posit, String negative, final onDialogClickListener listener){
        if (activity!=null&&activity.isRunning()){
            new MaterialDialog.Builder(activity).title(title).content(message)
                    .positiveText(posit)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            listener.onPositive();
                        }
                    })
                    .negativeText(negative)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            listener.onNegative();
                        }
                    })
                    .cancelable(false)
                    .show();
        }
    }
    public static void showHintDialog(BaseActivity activity, String message){
        if (activity!=null&&activity.isRunning()){
            new MaterialDialog.Builder(activity).title("提示").content(message)
                    .positiveText("确认")
                    .show();
        }
    }

    public static void showError(Context context, String message){
        new MaterialDialog.Builder(context).title("提示").content(message)
                .positiveText("确认")
                .show();
    }

    public interface onDialogClickListener{
        void onPositive();
        void onNegative();
    }
}
