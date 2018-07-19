package com.example.lrd.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lrd on 2018/5/9.
 */

public class StudyLinearLayout extends ViewGroup {

    public StudyLinearLayout(Context context) {
        super(context);
    }

    public StudyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        //记录当前的高度位置
        int curHeight = t;
        //将子View逐个摆放
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int measuredHeight = childAt.getMeasuredHeight();
            int measuredWidth = childAt.getMeasuredWidth();
            //摆放子View，参数分别是子View矩形区域的左、上、右、下边
            childAt.layout(l,curHeight,l+measuredWidth,curHeight+measuredHeight);
            curHeight += measuredHeight;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        if (childCount == 0){
            setMeasuredDimension(0,0);
        }else {
            //如果宽高都是包裹内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
                //将所有的子View宽、高相加
                int width = getTotalWidth();
                int height  = getTotalHeight();
                setMeasuredDimension(width,height);
            } else if (heightMode == MeasureSpec.AT_MOST){//如果只有高度是包裹内容
                setMeasuredDimension(widthSize,getTotalHeight());
            }else if (widthMode == MeasureSpec.AT_MOST){//如果只有宽度是包裹内容
                setMeasuredDimension(getTotalWidth(),heightSize);
            }
        }
    }

    private int getTotalWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getMeasuredWidth() > maxWidth){
                maxWidth = childAt.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    private int getTotalHeight() {
        int childCount = getChildCount();
        int totalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            totalHeight += childAt.getMeasuredHeight();
        }
        return totalHeight;
    }
}
