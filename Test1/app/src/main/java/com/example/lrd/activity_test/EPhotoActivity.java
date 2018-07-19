package com.example.lrd.activity_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lrd.R;
import com.example.lrd.utils.BitmapUtil;
import com.example.lrd.utils.PathUtils;
import com.example.lrd.view.GlideEngine;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.engine.ImageEngine;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lrd on 2018/4/26.
 */

public class EPhotoActivity extends AppCompatActivity {
    @BindView(R.id.choose_btn)
    Button chooseBtn;
    @BindView(R.id.choose_btn2)
    Button chooseBtn2;
    @BindView(R.id.choose_img)
    ImageView chooseImg;
    private String mPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_photo_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        //EasyPhotos.addWatermark(Bitmap watermark, Bitmap image, int srcImageWidth, int offsetX, int offsetY, boolean addInLeft);
        //EasyPhotos.createCamera(this);//参数说明：上下文
        //EasyPhotos.createAlbum(this, false, GlideEngine.getInstance());//参数说明：上下文，是否显示相机按钮，图片加载引擎实现(ImageEngine说明)

    }

    @OnClick({R.id.choose_btn, R.id.choose_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.choose_btn:
                EasyPhotos.createAlbum(this, true, GlideEngine.getInstance())//参数说明：上下文，
                        .setFileProviderAuthority("com.example.lrd.fileprovider")//参数说明：见下方`FileProvider的配置`
                        .setCount(22)//参数说明：最大可选数，默认1
                        .start(101);
                break;
            case R.id.choose_btn2:
                if (TextUtils.isEmpty(mPath)){
                    return;
                }
//                boolean isVip = true;//假设获取用户信息发现该用户不是vip
//                EasyPhotos.createAlbum(this, false, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
//                        .setCount(9)//参数说明：最大可选数，默认1
//                        .setOriginalMenu(false, isVip, "该功能为VIP会员特权功能")//参数说明：是否默认选中状态，是否可用，不可用时用户点击将toast信息。是否默认选中可以根据EasyPhotos的回调走，回调中会给出用户上一次是否选择了原图选项的标识
//                        .start(101);

                Bitmap watermark = BitmapFactory.decodeResource(getResources(), R.drawable.watermark).copy(Bitmap.Config.RGB_565, true);
                Bitmap bitmap = BitmapFactory.decodeFile(mPath).copy(Bitmap.Config.ARGB_8888, true);

                //给图片添加水印的api
                EasyPhotos.addWatermark(watermark, bitmap, 1080, 20, 20, false);

                chooseImg.setImageBitmap(bitmap);
                //Toast.makeText(SampleActivity.this, "水印在左下角", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            //相机或相册回调
            //返回图片地址集合：如果你只需要获取图片的地址，可以用这个
            ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
            //返回图片地址集合时如果你需要知道用户选择图片时是否选择了原图选项，用如下方法获取
            boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);

            mPath = resultPaths.get(0);
            Bitmap bitmap = BitmapUtil.toCompress(mPath);
            chooseImg.setImageBitmap(bitmap);
        }
    }

}
