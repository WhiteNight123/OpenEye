package com.example.openeye.ui.feed

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.ui.video.VideoActivity


class FeedFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this)[FeedFragmentViewModel::class.java] }
    lateinit var linearLayout: LinearLayout
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: FeedRecyclerAdapter
    lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var mTvError: TextView
    lateinit var mIvError: ImageView
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 延迟动画
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        recyclerView = view.findViewById(R.id.feed_rv)
        swipeRefresh = view.findViewById(R.id.feed_srl_refresh)
        mTvError = view.findViewById(R.id.feed_tv_net_error)
        mIvError = view.findViewById(R.id.feed_iv_net_error)
        linearLayout = view.findViewById(R.id.feed_constrain_layout)

        initView()
        initObserve()
    }

    private fun initView() {
        swipeRefresh.isRefreshing = true
        swipeRefresh.setOnRefreshListener {
            viewModel.getFeed()
            viewModel.getBanner()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_view_fade_in
                )
            )
        rvAdapter =
            FeedRecyclerAdapter(viewModel.videoData, viewModel.bannerData) { view1, videoDetail ->
                startActivity(view1, videoDetail)
            }
        recyclerView.adapter = rvAdapter
        var lastVisibleItem = 0
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //判断是否滑动到底部
                    if (!rvAdapter.isFadeTips() && lastVisibleItem + 1 == rvAdapter.itemCount) {
                        Log.e("TAG", "onScrollStateChanged: haha1")
                        viewModel.videoData[viewModel.videoData.size - 1].nextPageUrl?.let {
                            viewModel.getNextFeed(it)
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // findLastVisibleItemPosition找到界面的最后一个位置
                lastVisibleItem = layoutManager.findLastVisibleItemPosition()
            }
        })
    }

    private fun initObserve() {
        viewModel.banner.observe(viewLifecycleOwner) { it1 ->
            viewModel.bannerData.clear()
            viewModel.bannerData.addAll(it1)
            // 防止出现adapter还没加载的空指针
            if (::rvAdapter.isInitialized) {
                rvAdapter.getVHolder(0) {
                    it.banner.adapter?.notifyDataSetChanged()
                }
            }
        }
        viewModel.refresh.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.videoData.clear()
                mIvError.visibility = View.INVISIBLE
                mTvError.visibility = View.INVISIBLE
                if (::rvAdapter.isInitialized) {
                    rvAdapter.fadeTip = true
                }
                linearLayout.visibility = View.VISIBLE
            } else {
                if (::rvAdapter.isInitialized) {
                    rvAdapter.fadeTip = false
                }
                linearLayout.visibility = View.INVISIBLE
                mIvError.visibility = View.VISIBLE
                mTvError.visibility = View.VISIBLE
            }
            swipeRefresh.isRefreshing = false
        }
        viewModel.feedBean.observe(viewLifecycleOwner) {
            val current = viewModel.videoData.size
            viewModel.videoData.addAll(it)
            if (!this::layoutManager.isInitialized) {
                initRecyclerView()
            }
            rvAdapter.notifyDataSetChanged()
        }
        viewModel.feedNextBean.observe(viewLifecycleOwner) {
            viewModel.videoData.addAll(it)
            rvAdapter.notifyDataSetChanged()
        }
    }

    private fun startActivity(view: View, videoDetail: VideoDetailData) {
        val intent = Intent(context, VideoActivity::class.java)
        intent.putExtra("videoDetail", videoDetail)
        intent.putExtra("transitionName", view.transitionName)
        val options =
            ActivityOptions.makeSceneTransitionAnimation(activity, view, view.transitionName)
        startActivity(intent, options.toBundle())
    }

    companion object
}