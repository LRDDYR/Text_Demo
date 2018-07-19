package com.wlmq.profit.httpHelper;

/**
 * Created by lrd on 2017/12/13.
 * url
 */

public class Url {
    private static final String BASE_URL;



    static {
        switch (1) {
            case Environment.$Environment.ENVIRONMENT_SIT:     //测试
                BASE_URL = "http://10.1.19.118:8021/pwxweb/";
                break;
            case Environment.$Environment.ENVIRONMENT_PREVIEW: //生产
                BASE_URL = "";
                break;
            default:
                BASE_URL = "http://10.1.19.118:8021/pwxweb/";
                break;
        }
    }

    public static final String HOME_DATA = BASE_URL + "UccbDDYingInit.do";
    public static final  String HOME_QUESTION = BASE_URL + "UccbDDYingQA.do?channelNo=h501";
    public static final  String ADD_UP_DATA = BASE_URL + "UccbDDYingHistoryEarn.do";
    public static final  String ADD_GET_KEY = BASE_URL + "SDKGenToken.do";
}
