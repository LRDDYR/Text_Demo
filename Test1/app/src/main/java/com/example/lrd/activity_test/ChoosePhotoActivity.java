package com.example.lrd.activity_test;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lrd.R;
import com.example.lrd.utils.BitmapUtil;
import com.example.lrd.utils.PathUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by lrd on 2018/1/25.
 * new AlertDialog.Builder(this)
 .setMessage("向用户说明为什么需要这些权限")
 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
request.proceed();
}
})
 .setNegativeButton("取消", new DialogInterface.OnClickListener() {
@Override
public void onClick(DialogInterface dialog, int which) {
request.cancel();
}
})
 .show();
 */
@RuntimePermissions
public class ChoosePhotoActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CHOOSE = 23;
    @BindView(R.id.choose_btn)
    Button chooseBtn;
    @BindView(R.id.choose_btn2)
    Button chooseBtn2;
    @BindView(R.id.choose_img)
    ImageView mImg;
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_photo_layout);
        ButterKnife.bind(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            List<Uri> uris = Matisse.obtainResult(data);
//            Uri uri = uris.get(0);
//            String path = PathUtils.getPathByUri(  ,uri);
//            bitmap = BitmapUtil.toCompress(path);
//            mImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            mImg.setImageBitmap(bitmap);
            Observable.create(new ObservableOnSubscribe<Bitmap>() {
                @Override
                public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                    List<Uri> uris = Matisse.obtainResult(data);
                    Uri uri = uris.get(0);
                    String path = PathUtils.getPathByUri(ChoosePhotoActivity.this,uri);
                    bitmap = BitmapUtil.toCompress(path);
                    e.onNext(bitmap);
                    e.onComplete();
                }
            }).subscribeOn(Schedulers.newThread())        //线程调度器,将发送者运行在子线程
            .observeOn(AndroidSchedulers.mainThread())     //接受者运行在主线程
            .subscribe(new Observer<Bitmap>() {
                @Override
                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                }

                @Override
                public void onNext(@io.reactivex.annotations.NonNull Bitmap bitmap) {
                    mImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    mImg.setImageBitmap(bitmap);
                }

                @Override
                public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
//            try {
//                Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                Log.w("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth() + " heigth:" + bm.getHeight()); // 输出图像数据
//                mImg.setImageBitmap(bm);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
    @OnClick({R.id.choose_btn, R.id.choose_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_btn:
                ChoosePhotoActivityPermissionsDispatcher.showCameraWithPermissionCheck(this);
                break;
            case R.id.choose_btn2:
                //ChoosePhotoActivityPermissionsDispatcher.showContactsWithPermissionCheck(this);
//                Matisse.from(ChoosePhotoActivity.this)
//                        .choose(MimeType.allOf())
//                        .capture(true)
//                        .captureStrategy(new CaptureStrategy(true,"com.example.lrd.fileprovider"))
//                        .theme(R.style.Matisse_Dracula)
//                        .countable(false)
//                        .maxSelectable(9)
//                        .thumbnailScale(1f)
//                        .imageEngine(new GlideEngine())
//                        .forResult(REQUEST_CODE_CHOOSE);
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ChoosePhotoActivityPermissionsDispatcher.onRequestPermissionsResult(ChoosePhotoActivity.this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showCamera() {
        // NOTE: Perform action that requires the permission. If this is run by PermissionsDispatcher, the permission will have been granted
        Matisse.from(ChoosePhotoActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(9)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true,"com.example.lrd.fileprovider"))//存储到哪里
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @OnShowRationale({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRationaleForCamera(PermissionRequest request) {
        //showRationaleDialog("此功能需要开启相机相册权限，是否允许？", request);
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void onCameraDenied() {
        //Toast.makeText(this, "您拒绝了当前权限的开启！", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE})
    void onCameraNeverAskAgain() {
        Toast.makeText(this, "由于您拒绝了开启权限，如需重新开启，请在设置中打开", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        if(bitmap != null && !bitmap.isRecycled()){
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        super.onDestroy();
    }
    public static void MyRecycle(Bitmap bmp){
        if(!bmp.isRecycled() && null!=bmp){
            bmp=null;
        }
    }
}
