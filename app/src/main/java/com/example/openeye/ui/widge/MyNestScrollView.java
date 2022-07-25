package com.example.openeye.ui.widge;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author WhiteNight(1448375249 @ qq.com)
 * @date 2022-07-24
 * @description
 */
public class MyNestScrollView extends NestedScrollView {
    public MyNestScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (target instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) target;
            if (recyclerView.canScrollVertically(1)) {
                scrollBy(0, dy);
                consumed[1] = dy;
            }
        }
//        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin + widthUsed, lp.width);
        Log.d("TAG", "measureChildWithMargins: childWidthMeasureSpec " + childWidthMeasureSpec);
        int usedTotal = getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin + heightUsed ;
        Log.d("TAG", "measureChildWithMargins: usedTotal " + usedTotal);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(parentHeightMeasureSpec) - usedTotal), MeasureSpec.UNSPECIFIED);
        Log.d("TAG", "measureChildWithMargins: childHeightMeasureSpec " + childHeightMeasureSpec);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }
}
