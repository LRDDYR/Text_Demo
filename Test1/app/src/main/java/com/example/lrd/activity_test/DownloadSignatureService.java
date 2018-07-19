package com.example.lrd.activity_test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * Created By LRD
 * on 2018/7/11
 */
public class DownloadSignatureService extends Service {
    private static final String TAG = "MyService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.e(TAG, "--------->onCreate: ");
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Logger.e(TAG, "--------->onStart: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e(TAG, "--------->onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Logger.e(TAG, "--------->onDestroy: ");
        super.onDestroy();

    }
}