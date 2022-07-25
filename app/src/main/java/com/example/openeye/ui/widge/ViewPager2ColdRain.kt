package com.example.openeye.ui.widge

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.openeye.R

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-25
 * @description
 */
class IndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val paint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
    }
    private var focusColor = Color.BLUE
    private var unfocusedColor = Color.GRAY
    private var dotBetweenDistance = 0f

    private var firstDotX: Float = 0f
    private var firstDotY: Float = 0f
    private var radius: Float = 0f

    private var dotCount = 5
        set(value) {
            field = value
            invalidate()
        }

    var currentFocusPosition = 1
        set(value) {
            field = value
            // 重绘
            invalidate()
        }

    init {
        if (attrs != null) {
            val ty = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView)
            focusColor = ty.getColor(
                R.styleable.IndicatorView_focus_color,
                focusColor
            )
            unfocusedColor = ty.getColor(
                R.styleable.IndicatorView_unfocused_color,
                unfocusedColor
            )
            dotBetweenDistance = ty.getDimension(
                R.styleable.IndicatorView_dot_between_distance,
                dotBetweenDistance
            )
            ty.recycle()
        }
    }

    fun bindBannerVp(viewPager2: ViewPager2, dataSize: Int) {
        viewPager2.post {
            dotCount = dataSize
            viewPager2.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        currentFocusPosition = position % dataSize + 1
                    }
                }
            )
            requestLayout()
            invalidate()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        radius = height / 2F
        firstDotX = radius
        firstDotY = height / 2F
        // wrap_content 自行计算宽度
        if (widthMode == MeasureSpec.AT_MOST) {
            width = (dotCount * radius * 2F + dotBetweenDistance * (dotCount - 1)).toInt()
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = unfocusedColor
        // 绘制点
        var x = firstDotX
        repeat(dotCount) {
            if (it + 1 == currentFocusPosition) {
                paint.color = focusColor
            }
            canvas.drawCircle(x, firstDotY, radius, paint)
            paint.color = unfocusedColor
            x += dotBetweenDistance + 2 * radius
        }
    }
}