package com.example.openeye.ui.explore.community

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.openeye.R
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.ui.widge.ViewPager2Indicator

class PictureDetailActivity : BaseActivity() {
    private val communityData by lazy { intent.getStringArrayListExtra("pictureData") }
    lateinit var viewPager2: ViewPager2
    lateinit var indicator: ViewPager2Indicator
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_detail)
        viewPager2 = findViewById(R.id.picture_vp2)
        indicator = findViewById(R.id.picture_vp2_indicator)
        toolbar = findViewById(R.id.picture_toolbar_detail)
        toolbar.title = "查看大图"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val adapter = PictureViewpagerAdapter(communityData!!)
        viewPager2.adapter = adapter
        indicator.bindBannerVp(viewPager2, communityData!!.size)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}