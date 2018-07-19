package com.example.lrd.mvp.login;

/**
 * Created by lrd on 2018/1/31.
 * Class Note:登陆View的接口，实现类也就是登陆的activity
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();
}
