package com.example.openeye.ui.widge

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-25
 * @description
 */
private const val MAX_ROTATION = 90f
private const val MIN_SCALE = 0.9f

class SquareBoxTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.apply {
            pivotY = height / 2f
            when {
                position < -1 -> {
                    rotationY = -MAX_ROTATION
                    pivotX = width.toFloat()
                }
                position <= 1 -> {
                    rotationY = position * MAX_ROTATION
                    if (position < 0) {
                        pivotX = width.toFloat()
                        val scale =
                            MIN_SCALE + 4f * (1f - MIN_SCALE) * (position + 0.5f) * (position + 0.5f)
                        scaleX = scale
                        scaleY = scale
                    } else {
                        pivotX = 0f
                        val scale =
                            MIN_SCALE + 4f * (1f - MIN_SCALE) * (position - 0.5f) * (position - 0.5f)
                        scaleX = scale
                        scaleY = scale
                    }
                }
                else -> {
                    rotationY = MAX_ROTATION
                    pivotX = 0f
                }
            }
        }
    }
}