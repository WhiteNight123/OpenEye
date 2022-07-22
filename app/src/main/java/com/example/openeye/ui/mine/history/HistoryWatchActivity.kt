package com.example.openeye.ui.mine.history

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.ui.base.BaseActivity
import com.example.openeye.ui.video.VideoActivity
import com.example.openeye.utils.toast

class HistoryWatchActivity : BaseActivity() {
    private val viewModel by lazy { ViewModelProvider(this)[HistoryWatchActivityViewModel::class.java] }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HistoryWatchRecyclerAdapter
    lateinit var mTvClean: TextView
    lateinit var mTvNoData: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_watch)
        recyclerView = findViewById(R.id.history_rv_watch)
        mTvClean = findViewById(R.id.history_tv_clean)
        mTvNoData = findViewById(R.id.history_tv_no_data)
        initRecyclerView()
        viewModel.getHistory()
        mTvClean.setOnClickListener {
            if (viewModel.historyWatchData.isEmpty()) {
                "还没有观看记录哦".toast()
            } else {
                viewModel.deleteHistory()
                viewModel.historyWatchData.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.historyWatch.observe {
            if (it.isEmpty()) {
                mTvNoData.visibility = View.VISIBLE
            } else {
                mTvNoData.visibility = View.INVISIBLE
                viewModel.historyWatchData.clear()
                viewModel.historyWatchData.addAll(it)
                adapter.notifyDataSetChanged()
            }
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
        adapter = HistoryWatchRecyclerAdapter(viewModel.historyWatchData) { view, videoBean ->
            startActivity(view, videoBean)

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