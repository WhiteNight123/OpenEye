package com.example.openeye.ui.explore.topic

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openeye.R
import com.example.openeye.logic.model.TopicData
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.ui.rank.RankDetailRvAdapter
import com.shuyu.gsyvideoplayer.GSYVideoManager

class TopicDetailActivity : BaseActivity() {
    private val viewModel by lazy { ViewModelProvider(this)[TopicDetailActivityViewModel::class.java] }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TopicDetailRecyclerAdapter
    lateinit var mIvCover: ImageView
    lateinit var mTvDescribe: TextView
    private val id by lazy {
        intent.getIntExtra("id", 692)
    }
    private val topicData: TopicData by lazy {
        intent.getSerializableExtra("topicData") as TopicData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)

        recyclerView = findViewById(R.id.topic_rv_detail)
        mTvDescribe = findViewById(R.id.topic_tv_detail_description)
        mIvCover = findViewById(R.id.topic_iv_detail_cover)
        Glide.with(mIvCover).load(topicData.videoCover).into(mIvCover)
        initRecyclerView()
        viewModel.getDetailTopic(id)
        viewModel.topicDetailBean.observe(this) {
            mTvDescribe.text = it[0].nextPageUrl
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
        adapter = TopicDetailRecyclerAdapter(this, viewModel.topicDetailData)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstVisibleItem = 0
            var lastVisibleItem = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                //大于0说明有播放
                if (GSYVideoManager.instance().playPosition >= 0) {
                    //当前播放的位置
                    val position = GSYVideoManager.instance().playPosition
                    //对应的播放列表TAG
                    if (GSYVideoManager.instance().playTag == RankDetailRvAdapter.TAG && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if (!GSYVideoManager.isFullState(this@TopicDetailActivity)) {
                            GSYVideoManager.releaseAllVideos()
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }


    // 因为两个fragment公用一个生命周期,所以界面回来时不进行播放
    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()

        GSYVideoManager.releaseAllVideos()
    }

}