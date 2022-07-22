package com.example.openeye.ui.explore.category

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.CategoryData
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.ui.video.VideoActivity


class CategoryDetailActivity : BaseActivity() {
    private val viewModel by lazy { ViewModelProvider(this)[CategoryDetailActivityViewModel::class.java] }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CategoryDetailRecyclerAdapter
    lateinit var toolBar: Toolbar
    lateinit var imageView: ImageView
    private val categoryData by lazy {
        intent.getSerializableExtra("categoryData") as CategoryData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)
        val layoutParams = window.attributes
        layoutParams.flags =
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or layoutParams.flags

        recyclerView = findViewById(R.id.category_rv_detail)
        toolBar = findViewById(R.id.category_toolbar)
        imageView = findViewById(R.id.category_iv_detail_image)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(imageView).load(categoryData.cover).centerCrop().into(imageView)
        initRecyclerView()
        toolBar.title = "categoryData.title"
        viewModel.getCategory(categoryData.id)
        viewModel.categoryDetailBean.observe(this) {
            viewModel.categoryData.clear()
            viewModel.categoryData.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.recycler_view_fade_in
                )
            )
        adapter = CategoryDetailRecyclerAdapter(viewModel.categoryData) { view1, categoryDetail ->
            startActivity(view1, categoryDetail)
        }
        recyclerView.adapter = adapter

    }

    private fun startActivity(view: View, videoDetail: VideoDetailData) {
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra("videoDetail", videoDetail)
        intent.putExtra("transitionName", view.transitionName)
        val options =
            ActivityOptions.makeSceneTransitionAnimation(this, view, view.transitionName)
        startActivity(intent, options.toBundle())
    }
}