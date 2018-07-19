package com.example.lrd.mvp.login;

/**
 * Created by lrd on 2018/1/31.
 * Class Note:模拟登陆的操作的接口，实现类为LoginModelImpl.相当于MVP模式中的Model层
 */

public interface LoginModel {
    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

    void login(String userName,String passWord,OnLoginFinishedListener listener);
}
