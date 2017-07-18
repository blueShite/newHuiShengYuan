package com.xiaoyuan.campus_order.CustomView;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

/**
 * Created by longhengyu on 2017/7/3.
 */

public class IntegralCircleLineLayout extends ConstraintLayout {
    public IntegralCircleLineLayout(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
    }

    public IntegralCircleLineLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntegralCircleLineLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        // 高度和宽度一样
        int childHeightSize = (int) (childWidthSize*0.644)+(int)(70*scale);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}