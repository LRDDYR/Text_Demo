package com.example.lrd.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class StudyRegionView extends View {
    public StudyRegionView(Context context) {
        this(context,null);
    }

    public StudyRegionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StudyRegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);

//        Region region = new Region(100,100,300,300);
//        region.set(200,200,500,500);
//        drawRegion(canvas, region, paint);

        Path path = new Path();
        RectF rectF = new RectF(100, 100, 300, 200);
        path.addOval(rectF, Path.Direction.CW);
        //SetPath时,传入一个比椭圆区域小的矩形区域,让其取交集
        Region region = new Region();
        region.setPath(path,new Region(100,100,300,150));
        drawRegion(canvas,region,paint);
    }

    private void drawRegion(Canvas canvas, Region region, Paint paint) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rectF = new Rect();
        while (regionIterator.next(rectF)){
            canvas.drawRect(rectF,paint);
        }
    }
}
