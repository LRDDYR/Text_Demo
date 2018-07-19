package com.example.lrd.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created By LRD
 * on 2018/5/18
 */
public class FlowLayoutTest extends ViewGroup {
    public FlowLayoutTest(Context context) {
        this(context,null);
    }

    public FlowLayoutTest(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayoutTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int lineWidth = 0;
        int lineHeight = 0;
        int width = 0;
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lp = null;
            if (child.getLayoutParams() instanceof MarginLayoutParams){
                lp = (MarginLayoutParams)child.getLayoutParams();
            }
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth <= measureWidth){
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }else {
                width = Math.max(width,lineWidth);
                height += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;
            }
            if (i == getChildCount()-1){
                width = Math.max(width,lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension(measureWidthMode == MeasureSpec.EXACTLY ? measureWidth : width,
                measureHeightMode == MeasureSpec.EXACTLY ? measureHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineWidth = 0;
        int lineHeight = 0;
        int left = 0, top = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth <= getMeasuredWidth()){
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }else {
                left = 0;
                top += lineHeight;
                lineWidth = childWidth;
                lineHeight = childHeight;
            }
            int lc = left + lp.rightMargin;
            int tc = top + lp.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();
            child.layout(lc,tc,rc,bc);
            left += childWidth;
        }
    }
}
