package com.example.lrd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.lrd.R;
import com.example.lrd.activity_test.CalendarActivity;
import com.example.lrd.activity_test.ChoosePhotoActivity;
import com.example.lrd.activity_test.CustomActivity;
import com.example.lrd.activity_test.CustomViewActivity;
import com.example.lrd.activity_test.DropDownActivity;
import com.example.lrd.activity_test.EPhotoActivity;
import com.example.lrd.activity_test.GetDateActivity;
import com.example.lrd.activity_test.ListViewActivity;
import com.example.lrd.activity_test.MyFeedbackActivity;
import com.example.lrd.activity_test.RxJavaActivity;
import com.example.lrd.activity_test.ServiceActivity;
import com.example.lrd.activity_test.SingleSelectActivity;
import com.example.lrd.activity_test.SmartRefreshActivity;
import com.example.lrd.activity_test.ToolBarActivity;
import com.example.lrd.activity_test.WebActivity;
import com.example.lrd.hotel.PeroidSelectedActivity;
import com.example.lrd.mvp.login.MVPLoginActivity;
import com.vondear.rxtools.RxActivityTool;
import com.vondear.rxtools.RxTool;
import com.vondear.rxtools.activity.ActivityCodeTool;
import com.vondear.rxtools.activity.ActivityScanerCode;
import com.vondear.rxtools.activity.ActivityWebView;
import com.vondear.rxtools.interfaces.OnSuccessAndErrorListener;
import com.vondear.rxtools.module.alipay.AliPayModel;
import com.vondear.rxtools.module.alipay.AliPayTools;
import com.vondear.rxtools.module.wechat.pay.WechatModel;
import com.vondear.rxtools.module.wechat.pay.WechatPayTools;
import com.vondear.rxtools.view.RxToast;
import com.wlmq.profit.activity.HomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.GV)
    GridView mGridView;
    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
        initListener();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add("1");mList.add("2");mList.add("3");mList.add("Calendar");mList.add("5");mList.add("6");
        mList.add("7");mList.add("Matisse");mList.add("JSBridge");mList.add("MVP");mList.add("解锁");mList.add("RXJAVA");
        mList.add("EasyPhoto");mList.add("二维码");mList.add("AliPay");mList.add("WechatPay");
        mList.add("webView");mList.add("CosCalendar");mList.add("HotelCalendar");mList.add("ToolBar");mList.add("CustomView");
        mList.add("Dropdown");mList.add("Service");
    }

    private void initView() {
        RxTool.init(MainActivity.this);
        MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter();
        mGridView.setAdapter(myGridViewAdapter);
    }

    private void initListener(){
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                //ToastUtils.getInstanc(MainActivity.this).showToast(itemAtPosition);
                if ("1".endsWith(str)){
                    startActivity(new Intent(MainActivity.this,SlidingActivity.class));
                }else if ("2".equals(str)){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }else if ("3".equals(str)){
                    startActivity(new Intent(MainActivity.this,ObjectAnActivity.class));
                }else if ("Calendar".equals(str)){
                    startActivity(new Intent(MainActivity.this,GetDateActivity.class));
                }else if ("5".equals(str)){
                    startActivity(new Intent(MainActivity.this,CustomActivity.class));
                }else if ("6".equals(str)){
                    startActivity(new Intent(MainActivity.this,ListViewActivity.class));
                }else if ("7".equals(str)){
                    startActivity(new Intent(MainActivity.this,SmartRefreshActivity.class));
                }else if ("Matisse".equals(str)){
                    startActivity(new Intent(MainActivity.this,ChoosePhotoActivity.class));
                }else if ("JSBridge".equals(str)){//JSBridge
                    startActivity(new Intent(MainActivity.this,WebActivity.class));
                }else if ("MVP".equals(str)){
                    startActivity(new Intent(MainActivity.this,MVPLoginActivity.class));
                }else if ("解锁".equals(str)){
                    startActivity(new Intent(MainActivity.this,MyFeedbackActivity.class));
                }else if ("RXJAVA".equals(str)){
                    startActivity(new Intent(MainActivity.this,RxJavaActivity.class));
                }else if ("EasyPhoto".equals(str)){
                    startActivity(new Intent(MainActivity.this,EPhotoActivity.class));
                }else if ("二维码".equals(str)){
                    RxActivityTool.skipActivity(MainActivity.this, ActivityScanerCode.class);
                }else if ("webView".equals(str)){
                    RxActivityTool.skipActivity(MainActivity.this, ActivityWebView.class);
                }else if ("AliPay".equals(str)){
                    AliPayTools.aliPay(MainActivity.this, "adfhadfoinavaj12k",//支付宝分配的APP_ID
                            false,//是否是 RSA2 加密
                            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIhIs/3wz/nod7Ff/0UMzyK4gRCjPLqSfYkxxtlLn8GEn5Tg9kgKEl+CBiVad3w1afgFivaTHHI7xCC9zyulFkKQ3Q5IuouBkaY2+hKUPDzRRer3RmxUcNM4e5IUfDwG//8Hh69Q0kEHyD22lXGvo/kQnoUyhH+RjZ1UVAJAzj7lAgMBAAECgYAVh26vsggY0Yl/Asw/qztZn837w93HF3cvYiaokxLErl/LVBJz5OtsHQ09f2IaxBFedfmy5CB9R0W/aly851JxrI8WAkx2W2FNllzhha01fmlNlOSumoiRF++JcbsAjDcrcIiR8eSVNuB6ymBCrx/FqhdX3+t/VUbSAFXYT9tsgQJBALsHurnovZS1qjCTl6pkNS0V5qio88SzYP7lzgq0eYGlvfupdlLX8/MrSdi4DherMTcutUcaTzgQU20uAI0EMyECQQC6il1Kdkw8Peeb0JZMHbs+cMCsbGATiAt4pfo1b/i9/BO0QnRgDqYcjt3J9Ux22dPYbDpDtMjMRNrAKFb4BJdFAkBMrdWTZOVc88IL2mcC98SJcII5wdL3YSeyOZto7icmzUH/zLFzM5CTsLq8/HDiqVArNJ4jwZia/q6Fg6e8KO2hAkB0EK1VLF/ox7e5GkK533Hmuu8XGWN6I5bHnbYd06qYQyTbbtHMBrFSaY4UH91Qwd3u9gAWqoCZoGnfT/o03V5lAkBqq8jZd2lHifey+9cf1hsHD5WQbjJKPPIb57CK08hn7vUlX5ePJ02Q8AhdZKETaW+EsqJWpNgsu5wPqsy2UynO",// RSA 或 RSA2 字符串
                            new AliPayModel("12389382237484872",//订单ID (唯一)
                                    "money",//价格
                                    "name",//商品名称
                                    "detail"),//商品描述详情 (用于显示在 支付宝 的交易记录里)
                            new OnSuccessAndErrorListener() {
                                @Override
                                public void onSuccess(String s) {
                                    RxToast.success("支付成功");
                                }

                                @Override
                                public void onError(String s) {
                                    RxToast.error("支付失败");
                                }
                            });
                } else if ("WechatPay".equals(str)) {
                    WechatPayTools.wechatPayUnifyOrder(MainActivity.this,
                            "dfhafa", //微信分配的APP_ID
                            "adfhadfoinavaj12k", //微信分配的 PARTNER_ID (商户ID)
                            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIhIs/3wz/nod7Ff/0UMzyK4gRCjPLqSfYkxxtlLn8GEn5Tg9kgKEl+CBiVad3w1afgFivaTHHI7xCC9zyulFkKQ3Q5IuouBkaY2+hKUPDzRRer3RmxUcNM4e5IUfDwG//8Hh69Q0kEHyD22lXGvo/kQnoUyhH+RjZ1UVAJAzj7lAgMBAAECgYAVh26vsggY0Yl/Asw/qztZn837w93HF3cvYiaokxLErl/LVBJz5OtsHQ09f2IaxBFedfmy5CB9R0W/aly851JxrI8WAkx2W2FNllzhha01fmlNlOSumoiRF++JcbsAjDcrcIiR8eSVNuB6ymBCrx/FqhdX3+t/VUbSAFXYT9tsgQJBALsHurnovZS1qjCTl6pkNS0V5qio88SzYP7lzgq0eYGlvfupdlLX8/MrSdi4DherMTcutUcaTzgQU20uAI0EMyECQQC6il1Kdkw8Peeb0JZMHbs+cMCsbGATiAt4pfo1b/i9/BO0QnRgDqYcjt3J9Ux22dPYbDpDtMjMRNrAKFb4BJdFAkBMrdWTZOVc88IL2mcC98SJcII5wdL3YSeyOZto7icmzUH/zLFzM5CTsLq8/HDiqVArNJ4jwZia/q6Fg6e8KO2hAkB0EK1VLF/ox7e5GkK533Hmuu8XGWN6I5bHnbYd06qYQyTbbtHMBrFSaY4UH91Qwd3u9gAWqoCZoGnfT/o03V5lAkBqq8jZd2lHifey+9cf1hsHD5WQbjJKPPIb57CK08hn7vUlX5ePJ02Q8AhdZKETaW+EsqJWpNgsu5wPqsy2UynO"
                            , //微信分配的 PRIVATE_KEY (私钥)
                            new WechatModel("12389382237484872", //订单ID (唯一)
                                    "121", //价格
                                    "ewr", //商品名称
                                    "sfs"), //商品描述详情
                            new OnSuccessAndErrorListener() {
                                @Override
                                public void onSuccess(String s) {

                                }

                                @Override
                                public void onError(String s) {

                                }
                            });
                }else if ("CosCalendar".contains(str)){
                    startActivity(new Intent(MainActivity.this,CalendarActivity.class));
                }else if ("HotelCalendar".contains(str)){
                    startActivity(new Intent(MainActivity.this,PeroidSelectedActivity.class));
                }else if ("ToolBar".equals(str)){
                    startActivity(new Intent(MainActivity.this,ToolBarActivity.class));
                }else if ("CustomView".equals(str)){
                    startActivity(new Intent(MainActivity.this,CustomViewActivity.class));
                }else if("Dropdown".endsWith(str)){
                    startActivity(new Intent(MainActivity.this,DropDownActivity.class));
                }else if("Service".endsWith(str)){
                    startActivity(new Intent(MainActivity.this,ServiceActivity.class));
                }
            }
        });
    }


    private class MyGridViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_gridview,null);
                holder.mTv = convertView.findViewById(R.id.item_gv_text);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTv.setText(mList.get(position));
            return convertView;
        }

        class ViewHolder{
            TextView mTv;
        }
    }
}
