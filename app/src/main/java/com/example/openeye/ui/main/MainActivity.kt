package com.example.openeye.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.openeye.R
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.ui.feed.FeedFragment
import com.example.openeye.ui.home.HomeFragment
import com.example.openeye.ui.video.VideoActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.abs

class MainActivity : BaseActivity() {

    private lateinit var viewpager: ViewPager2
    lateinit var toolbar: MaterialToolbar
    lateinit var searchIv: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        viewpager = findViewById<ViewPager2>(R.id.main_vp)
        searchIv = findViewById(R.id.main_iv_search)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        toolbar = findViewById(R.id.toolbar)
        val badge =
            bottomNavigationView.getOrCreateBadge(bottomNavigationView.menu.getItem(3).itemId)
        badge.number = 2000
        toolbar.apply {
            title = "Banzai"
        }
        searchIv.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            //val pair1 = Pair.create(view, view.transitionName)
            //val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2, pair3)
            startActivity(intent)
        }
        val animatorVp = ViewPager2.PageTransformer { page, position ->
            val absPos = abs(position)
            page.apply {
                //translationY = absPos * 100f
                val scale = if (absPos > 1) 0f else 1 - absPos / 3
                scaleX = scale
                scaleY = scale
            }
        }
        viewpager.setPageTransformer(animatorVp)
        val mFragments = ArrayList<Fragment>()
        mFragments.add(FeedFragment())
        mFragments.add(HomeFragment())
        mFragments.add(HomeFragment())
        mFragments.add(HomeFragment())
        viewpager.adapter = MyFragmentPagerAdapter(this, mFragments)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_menu1 -> {
                    viewpager.currentItem = 0
                }
                R.id.navigation_menu2 -> {
                    viewpager.currentItem = 1
                }
                R.id.navigation_menu3 -> {
                    viewpager.currentItem = 2
                }
                R.id.navigation_menu4 -> {
                    viewpager.currentItem = 3
                }
            }
            true
        }

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavigationView.menu.getItem(position).isChecked = true
                when (position) {
                    0 -> toolbar.title = "日报"
                    1 -> toolbar.title = "探索"
                    2 -> toolbar.title = "热门"
                    3 -> toolbar.title = "我的"
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    private var mExitTime: Long = 0

}