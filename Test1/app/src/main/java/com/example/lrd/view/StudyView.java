package com.example.lrd.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lrd on 2018/5/9.
 */

public class StudyView extends AppCompatTextView {
    public StudyView(Context context) {
        super(context);
    }

    public StudyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StudyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(100, widthMeasureSpec);
        int height = getMySize(100, heightMeasureSpec);

        if (width < height){
            height = width;
        }else {
            width = height;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int r = getMeasuredWidth() / 2;//半径
        //圆心横坐标
        int centerX = r;
        //圆心纵坐标
        int centerY = r ;

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        //开始绘制
        canvas.drawCircle(centerX,centerY,r,paint);
    }

    protected int getMySize(int defaultSize, int measureSpec){
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode){
            case MeasureSpec.UNSPECIFIED://如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            case MeasureSpec.AT_MOST://如果测量模式是最大取值为size,我们将大小取值为size,也可以取其他值
                mySize = size;
                break;
            case MeasureSpec.EXACTLY://如果是固定大小，那就不要去改变它
                mySize = size;
                break;
        }
        return mySize;
    }
}
