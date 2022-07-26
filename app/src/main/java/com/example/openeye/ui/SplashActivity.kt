package com.example.openeye.ui

import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.example.openeye.R
import com.google.android.material.textview.MaterialTextView

/**
 * 启动页Activity
 * 不知道为什么SplashScreen失效,只能拿一个Activity作启动图
 */
class SplashActivity : AppCompatActivity() {
    lateinit var ivIcon: ImageView
    lateinit var ivBrand: ImageView
    lateinit var tvCn: MaterialTextView
    lateinit var tvZn: AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ivIcon = findViewById(R.id.splash_iv_icon)
        tvCn = findViewById(R.id.splash_tv_cn)
        tvZn = findViewById(R.id.splash_tv_zn)
        ivBrand = findViewById(R.id.splash_iv_brand)
        (ivIcon.drawable as Animatable).start()
        tvCn.animate().alpha(1f).setDuration(800).start()
        tvZn.animate().alpha(1f).setDuration(800).start()
        ivBrand.animate().scaleX(1.2f).scaleY(1.2f).setDuration(800).start()
        Thread {
            Thread.sleep(1500)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }.start()

    }
}