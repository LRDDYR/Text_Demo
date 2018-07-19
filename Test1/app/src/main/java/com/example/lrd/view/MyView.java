package com.example.lrd.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lrd on 2018/5/10.
 */

public class MyView extends View {

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);//样式
        paint.setStrokeWidth(5);//画笔宽度
//        paint.setShadowLayer(10,15,15,Color.GREEN);
        //设置画布背景色
//        canvas.drawColor(Color.YELLOW);

        //canvas.drawCircle(200,200,150,paint);//画圆
        //canvas.drawLine(200,200,400,400,paint);//画直线
        //float[] pts = {90,90,300,300,85,85,220,260};//多条直线
        //canvas.drawLines(pts,paint);
        //canvas.drawPoint(100,100,paint);//画点
        //float[] pts = {100,100,260,340};//画多个点
        //canvas.drawPoints(pts,paint);

        //画矩形
        //canvas.drawRect(10,10,100,100,paint);
        //Rect rect = new Rect(110,10,210,100);
        //canvas.drawRect(rect,paint);
        //RectF rectF = new RectF(230, 10, 330, 100);
        //canvas.drawRect(rectF,paint);

        //圆角矩形
        //RectF rectF = new RectF(100, 10, 300, 300);
        //canvas.drawRoundRect(rectF,60,60,paint);

        //圆形
        //canvas.drawCircle(160,160,100,paint);

        //椭圆
        //RectF rectF = new RectF(100,10,300,100);
        //canvas.drawOval(rectF,paint);

        //弧
        //RectF rectF = new RectF(100, 10, 300, 100);
        //canvas.drawArc(rectF,0,90,false,paint);

        // ***绘制路径***
        //直线路径
//        Path path = new Path();
//        path.moveTo(10,10);
//        path.lineTo(10,100);
//        path.lineTo(350,100);
//        path.lineTo(500,100);
//        path.lineTo(600,200);
//        path.close();
//        canvas.drawPath(path,paint);

        //矩形路径
//        Path path = new Path();
//        RectF rectF = new RectF(100, 100, 300, 300);
//        path.addRect(rectF, Path.Direction.CCW);//逆向生成
//
//        Path path1 = new Path();
//        RectF rectF1 = new RectF(350, 100, 550, 300);
//        path1.addRect(rectF1, Path.Direction.CW);//顺时针生成
//
//        canvas.drawPath(path, paint);
//        canvas.drawPath(path1, paint);
//
//        String str = "风萧萧兮易水寒，壮士一去兮不复还";
//        paint.setColor(Color.GREEN);
//        paint.setTextSize(30);
//        canvas.drawTextOnPath(str,path,0,20,paint);
//        canvas.drawTextOnPath(str,path1,0,20,paint);

        //圆角矩形路径
//        Path path = new Path();
//        RectF rectF = new RectF(100, 100, 300, 300);
//        path.addRoundRect(rectF,10,10, Path.Direction.CW);
//
//        RectF rectF1 = new RectF(310, 100, 510, 300);
//        float[] radii = {10,10,50,50,100,100,15,15};
//        path.addRoundRect(rectF1,radii, Path.Direction.CW);
//
//        canvas.drawPath(path,paint);

        //圆形路径
//        Path path = new Path();
//        path.addCircle(300,300,200, Path.Direction.CW);
//        canvas.drawPath(path,paint);

        //椭圆路径
//        Path path = new Path();
//        RectF rectF = new RectF(100, 100, 500, 200);
//        path.addOval(rectF, Path.Direction.CW);
//        canvas.drawPath(path,paint);

        //弧形路径
//        Path path = new Path();
//        RectF rectF = new RectF(100, 100, 300, 300);
//        path.addArc(rectF,0,355);
//        canvas.drawPath(path,paint);

        //指定个个文字位置
//        paint.setTextSize(30);
//        paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充
//        float[] pos = {100,100,100,200,100,300};
//        canvas.drawPosText("风萧萧",pos,paint);
    }
}
