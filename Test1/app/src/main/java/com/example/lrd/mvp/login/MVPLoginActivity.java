package com.example.lrd.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.lrd.R;
import com.example.lrd.mvp.main.MVPMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lrd on 2018/1/31.
 * Class Note:MVP设计模式
 */

public class MVPLoginActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.button)
    Button mLoginBtn;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    private LoginPresenterImpl mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mvp_login_layout);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        mUsername.setError("用户名错误");
    }

    @Override
    public void setPasswordError() {
        mPassword.setError("用户密码错误");
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MVPMainActivity.class));
        //finish();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mPresenter.validateCredentials(mUsername.getText().toString(), mPassword.getText().toString());
    }
}
