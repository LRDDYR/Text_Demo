package com.example.lrd.mvp.login;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by lrd on 2018/1/31.
 * Class Note:延时模拟登陆（2s），如果名字或者密码为空则登陆失败，否则登陆成功
 */

public class LoginModelImpl implements LoginModel{
    @Override
    public void login(final String userName, final String passWord, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(userName)){
                    listener.onUsernameError();
                    return;
                }
                if (TextUtils.isEmpty(passWord)){
                    listener.onPasswordError();
                    return;
                }
                listener.onSuccess();
            }
        },3000);
    }
}
