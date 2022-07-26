package com.example.openeye.ui.widge

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author WhiteNight(1448375249 @ qq.com)
 * @date 2022-07-24
 * @description 用来解决滑动冲突, 后来发现不用NestScrollView了
 */
class MyNestScrollView : NestedScrollView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (target is RecyclerView) {
            if (target.canScrollVertically(1)) {
                scrollBy(0, dy)
                consumed[1] = dy
            }
        }
        //        super.onNestedPreScroll(target, dx, dy, consumed, type);
    }

    override fun measureChildWithMargins(
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ) {
        val lp = child.layoutParams as MarginLayoutParams
        val childWidthMeasureSpec = getChildMeasureSpec(
            parentWidthMeasureSpec,
            paddingLeft + paddingRight + lp.leftMargin + lp.rightMargin + widthUsed,
            lp.width
        )
        Log.d("TAG", "measureChildWithMargins: childWidthMeasureSpec $childWidthMeasureSpec")
        val usedTotal = paddingTop + paddingBottom + lp.topMargin + lp.bottomMargin + heightUsed
        Log.d("TAG", "measureChildWithMargins: usedTotal $usedTotal")
        val childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
            Math.max(
                0,
                MeasureSpec.getSize(parentHeightMeasureSpec) - usedTotal
            ), MeasureSpec.UNSPECIFIED
        )
        Log.d("TAG", "measureChildWithMargins: childHeightMeasureSpec $childHeightMeasureSpec")
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
    }

}