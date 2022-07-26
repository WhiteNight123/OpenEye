package com.example.openeye.ui.widge

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-26
 * @description
 */
class BannerTransformer : ViewPager2.PageTransformer {
    private val default = 14 / 15f
    private val defaultTrans = 3.6f
    override fun transformPage(page: View, position: Float) {

//                    Log.e("TAG0000000", "initBanner: $position")
        when {
            position <= -1 -> {
//                            Log.e(" ViewPageTransform", "11111")
                page.scaleX = default
                page.scaleY = default
                page.translationX = defaultTrans
            }
            position <= 0 -> {
                // Log.e("ViewPageTransform", "22222222")
                page.scaleX = 1 + position / 15
                page.scaleY = 1 + position / 15
                page.translationX = (0 - position) * default
                //   page.translationY=(0-position)*default

            }
            position <= 1 -> {
                //Log.e("ViewPageTransform", "33333333")
                page.scaleX = 1 - position / 15
                page.scaleY = 1 - position / 15
                page.translationX = (0 - position) * default

            }
            else -> {
                // Log.e("ViewPageTransform", "4444444444444")
                page.scaleX = default
                page.scaleY = default
                page.translationX = defaultTrans

            }
        }
    }
}