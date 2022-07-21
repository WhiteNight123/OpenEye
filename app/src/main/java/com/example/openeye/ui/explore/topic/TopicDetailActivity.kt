package com.example.openeye.ui.explore.topic

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeye.R

class TopicDetailActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this)[TopicDetailFragmentViewModel::class.java] }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TopicDetailRecyclerAdapter
    private val id by lazy {
        intent.getIntExtra("videoDetail", 692)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)

        recyclerView = findViewById(R.id.topic_rv_detail)
        initRecyclerView()
        viewModel.getDetailTopic(id)
        viewModel.topicDetailBean.observe(this) {
            viewModel.topicDetailData.addAll(it)
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
        adapter = TopicDetailRecyclerAdapter(viewModel.topicDetailData)
        recyclerView.adapter = adapter
    }

}