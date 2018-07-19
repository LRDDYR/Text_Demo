package com.example.lrd.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created By LRD
 * on 2018/5/14
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        int lineWidth = 0;//每一行的宽度
        int lineHeight = 0;//每一行的高度
        int height = 0;//整个布局高度
        int width  = 0;//整个布局宽度

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);

            MarginLayoutParams lp = null;
            if (child.getLayoutParams() instanceof MarginLayoutParams){
                lp = (MarginLayoutParams) child.getLayoutParams();
            }
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            if (lineWidth + childWidth <= measureWidth){
                //不换行，累加值lineWidth,lineHeight取最大高度
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }else {
                //换行
                width = Math.max(width,lineWidth);
                height += lineHeight;
                //因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;
            }
            //最后一行
            if (i == childCount - 1){
                width = Math.max(width,lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension(measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : width ,
                measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int top = 0,left = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = null;
            if (child.getLayoutParams() instanceof MarginLayoutParams){
                lp = (MarginLayoutParams) child.getLayoutParams();
            }
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (childWidth + lineWidth <= getMeasuredWidth()){
                //不换行
                lineWidth += childWidth;
                lineHeight = Math.max(childHeight,lineHeight);
            }else {
                //换行,控件跑到下一行，从最左边开始，so left=0,而top需要加上上一行的行高，
                top += lineHeight;
                left = 0;
                //初始化lineHeight lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;
            }
            //计算子控件的 left top right bottom
            int lc = left + lp.leftMargin;
            int tc = top + lp.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();
            child.layout(lc,tc,rc,bc);
            //将left设置为下一个控件的起点
            left += childWidth;
        }
    }
}
