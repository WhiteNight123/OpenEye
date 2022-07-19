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
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.ui.video.VideoActivity
import com.example.openeye.ui.widge.FadeDotsIndicator


class FeedFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(FeedFragmentViewModel::class.java) }
    lateinit var banner: ViewPager2
    lateinit var constraintLayout: ConstraintLayout
    lateinit var bannerAdapter: BannerAdapter
    lateinit var bannerIndicator: FadeDotsIndicator
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: FeedRecyclerAdapter
    lateinit var nestedScrollView: NestedScrollView
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
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        recyclerView = view.findViewById(R.id.feed_rv)
        swipeRefresh = view.findViewById(R.id.feed_srl_refresh)
        mTvError = view.findViewById(R.id.feed_tv_net_error)
        mIvError = view.findViewById(R.id.feed_iv_net_error)
        banner = view.findViewById(R.id.banner_viewpager)
        bannerIndicator = view.findViewById(R.id.banner_indicator)
        constraintLayout = view.findViewById(R.id.feed_constrain_layout)
        nestedScrollView = view.findViewById(R.id.feed_nest_scroll_view)

        initBanner()
        initView()
        initObserve()
        viewModel.getBanner()
        viewModel.getFeed()
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
        rvAdapter = FeedRecyclerAdapter(viewModel.videoData) { view1, videoDetail ->
            startActivity(view1, videoDetail)
        }
        recyclerView.adapter = rvAdapter
        nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            //判断是否滑到的底部
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                viewModel.videoData[viewModel.videoData.size - 1].nextPageUrl?.let {
                    viewModel.getNextFeed(
                        it
                    )
                }
            }
        })
    }

    private fun initBanner() {
        bannerAdapter = BannerAdapter(viewModel.bannerData) { view, videoBean ->
            startActivity(view, videoBean)
        }
        banner.adapter = bannerAdapter
        banner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                banner.currentItem = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                //只有在空闲状态，才让自动滚动
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if (banner.currentItem == 0) {
                        banner.setCurrentItem(bannerAdapter.itemCount - 2, false)
                    } else if (banner.currentItem == bannerAdapter.itemCount - 1) {
                        banner.setCurrentItem(1, false)
                    }
                }
            }
        })
        val mLooper = object : Runnable {
            override fun run() {
                banner.currentItem = ++banner.currentItem
                banner.postDelayed(this, 2000)
            }
        }
        banner.postDelayed(mLooper, 2000)
    }

    private fun initObserve() {
        viewModel.banner.observe(viewLifecycleOwner) {
            viewModel.bannerData.clear()
            viewModel.bannerData.addAll(it)
            bannerIndicator.setViewPager2(banner)
            bannerAdapter.notifyItemRangeChanged(0, it.size - 1)
        }
        viewModel.refresh.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.videoData.clear()
                mIvError.visibility = View.INVISIBLE
                mTvError.visibility = View.INVISIBLE
                if (::rvAdapter.isInitialized) {
                    rvAdapter.fadeTip = true
                }
                constraintLayout.visibility = View.VISIBLE
            } else {
                if (::rvAdapter.isInitialized) {
                    rvAdapter.fadeTip = false
                }

                constraintLayout.visibility = View.INVISIBLE
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
            //rvAdapter.fadeTip = true

            Log.d("TAG", "onViewCreated: ${current - 1}    ${viewModel.videoData.size - 1}")
            rvAdapter.notifyDataSetChanged()
            //rvAdapter.notifyItemRangeChanged(0, viewModel.videoData.size)
        }
        viewModel.feedNextBean.observe(viewLifecycleOwner) {
            viewModel.videoData.addAll(it)
            rvAdapter.notifyDataSetChanged()
        }
    }

    private fun startActivity(view: View, videoDetail: VideoDetailsBean) {
        val intent = Intent(context, VideoActivity::class.java)
        intent.putExtra("videoDetail", videoDetail)
        intent.putExtra("transitionName", view.transitionName)
        val options =
            ActivityOptions.makeSceneTransitionAnimation(activity, view, view.transitionName)
        startActivity(intent, options.toBundle())
    }


    companion object {

    }
}