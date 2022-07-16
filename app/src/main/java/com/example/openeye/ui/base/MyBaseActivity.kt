package com.example.openeye.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

abstract class MyBaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ViewCompat.getWindowInsetsController(window.decorView)?.isAppearanceLightStatusBars =
            true // 设置状态栏字体颜色为黑色
    }

}