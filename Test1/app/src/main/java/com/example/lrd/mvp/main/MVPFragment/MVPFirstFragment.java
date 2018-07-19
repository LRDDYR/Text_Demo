package com.example.lrd.mvp.main.MVPFragment;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.example.lrd.R;
import com.example.lrd.test.adpter.WaveHelper;
import com.gelitenight.waveview.library.WaveView;
import com.wlmq.profit.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lrd on 2018/2/1.
 */

public class MVPFirstFragment extends MVPBaseFragment {
    @BindView(R.id.testText)
    TextView testText;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.wave)
    WaveView mWave;
    private List<Integer> localImages;
    private List<String> titles;
    private int mBorderColor = Color.parseColor("#3cf16d7a");
    private int mBorderWidth = 0;
    private WaveHelper mWaveHelper;

    @Override
    public int getLayout() {
        return R.layout.test_text_layout;
    }

    @Override
    protected void initView() {
        testText.setText("First");
        localImages = new ArrayList();
        localImages.add(R.drawable.profile_header_backdrop);
        localImages.add(R.drawable.banner_1);
        localImages.add(R.drawable.banner_2);
        localImages.add(R.drawable.banner_3);
        localImages.add(R.drawable.banner_4);
        titles = new ArrayList<>();
        titles.add("x1");
        titles.add("x2");
        titles.add("x3");
        titles.add("x4");
        titles.add("x5");
        initBanner();
        initWave();
    }

    private void initWave() {
        mWave.setWaveColor(
                Color.parseColor("#28f16d7a"),
                Color.parseColor("#3cf16d7a"));
        mBorderColor = Color.parseColor("#44f16d7a");
        mWave.setShapeType(WaveView.ShapeType.SQUARE);
        mWave.setBorder(mBorderWidth, mBorderColor);
        mWaveHelper = new WaveHelper(mWave);


    }

    private void initBanner() {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(localImages);
        //设置banner动画效果
        //mBanner.setBannerAnimation(Transformer.);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
//            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveHelper.start();
    }
}
