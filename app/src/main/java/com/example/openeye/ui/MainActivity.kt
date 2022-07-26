package com.example.openeye.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.openeye.R
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.ui.base.BaseFragmentVPAdapter
import com.example.openeye.ui.explore.ExploreFragment
import com.example.openeye.ui.feed.FeedFragment
import com.example.openeye.ui.mine.MineFragment
import com.example.openeye.ui.rank.RankFragment
import com.example.openeye.ui.search.SearchActivity
import com.example.openeye.ui.widge.ScaleInTransformer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.platform.MaterialSharedAxis

/**
 * 主页Activity,由CoordinatorLayout+ToolBar+ViewPager2+BottomNavigationView组成
 */
class MainActivity : BaseActivity() {
    private lateinit var viewpager: ViewPager2
    lateinit var toolbar: Toolbar
    lateinit var searchIv: ImageView
    lateinit var mTvTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 共享z轴动画(其实看不出什么区别)
        val exit = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            addTarget(com.google.android.material.R.id.container)
        }
        window.enterTransition = exit
        setContentView(R.layout.activity_main)

        viewpager = findViewById(R.id.main_vp)
        searchIv = findViewById(R.id.main_iv_search)
        mTvTitle = findViewById(R.id.toolbar_title)

        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.main_bottom_navigation_view)
        toolbar = findViewById(R.id.toolbar)

        searchIv.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            val bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, bundle)
        }
        viewpager.setPageTransformer(ScaleInTransformer())
        viewpager.adapter = BaseFragmentVPAdapter(
            this,
            listOf(
                FeedFragment(),
                ExploreFragment(),
                RankFragment(),
                MineFragment()
            )
        )
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
                    0 -> mTvTitle.text = "日报"
                    1 -> mTvTitle.text = "探索"
                    2 -> mTvTitle.text = "热门"
                    3 -> mTvTitle.text = "我的"
                }
            }
        })
    }

    // 再按一次推出程序
    private var mExitTime: Long = 0
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

}