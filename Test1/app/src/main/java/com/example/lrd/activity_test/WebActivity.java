package com.example.lrd.activity_test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lrd.R;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.wlmq.profit.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lrd on 2018/1/29.
 * webView H5
 */

public class WebActivity extends AppCompatActivity {
    @BindView(R.id.test_web_black)
    ImageView mBlack;
    @BindView(R.id.test_web_title)
    TextView mTitle;
    @BindView(R.id.test_web_btn)
    ImageView mBtn;
    @BindView(R.id.test_webView)
    BridgeWebView mWebView;
    @BindView(R.id.web_pb)
    ProgressBar webPb;
    private WebSettings webSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_web_layout);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        webPb.setMax(100);//设置加载进度最大值
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setWebChromeClient(new WebChromeClient());
        //mWebView.addJavascriptInterface(new AndroidtoJs(),"Native");
        // 如果不加这一行，当点击界面链接，跳转到外部时，会出现net::ERR_CACHE_MISS错误
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //http://10.1.20.11/O2O/journey/#/    "TeamDetail/"+praductbean.getGoodsId()+"/"+praductbean.getShopSeq()
        //mWebView.loadUrl("file:///android_asset/test.html");
        //http://10.1.16.45:8101/journey/#/TeamDetail/201801111221140010730004/1073
        //http://10.1.1.4:8989/#/
//        mWebView.loadUrl("http://10.1.1.4:8989/#/TeamDetail/201801111221140010730004/1073");
        //mWebView.loadUrl("http://www.baidu.com");
        // 设置setWebChromeClient对象
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mTitle.setText(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                webPb.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }
        });
        //设置此方法可在WebView中打开链接，反之用浏览器打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!webSetting.getLoadsImagesAutomatically()) {
                    webSetting.setLoadsImagesAutomatically(true);
                }
                webPb.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                webPb.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }
                // Otherwise allow the OS to handle things like tel, mailto, etc.
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        mWebView.loadUrl("http://www.baidu.com");
        /**
         * 前端发送消息给客户端  submitFromWeb 是js调用的方法名  安卓返回给js
         */
        mWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //显示接收的消息
                showToast(data);
                //返回给html的消息
                function.onCallBack("返回给Toast的alert");
            }
        });
        mWebView.registerHandler("setTitle", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //showToast(data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String title = jsonObject.optString("Title");
                    mTitle.setText(title);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mWebView.registerHandler("needgoback", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                showToast(data);
            }
        });


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 给Html发消息,js接收并返回数据
                 */
                mWebView.callHandler("functionInJs", "调用js的方法", new CallBackFunction() {

                    @Override
                    public void onCallBack(String data) {
                        showToast("===" + data);
                    }
                });
            }
        });
    }

    public void showToast(String msg) {
        ToastUtils.show(msg);
    }


    @OnClick({R.id.test_web_black, R.id.test_web_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.test_web_black:
                finish();
                break;
            case R.id.test_web_btn:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 处理返回键，在webview界面，按下返回键，不退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
