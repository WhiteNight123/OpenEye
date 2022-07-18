package com.example.openeye.ui.feed

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.ui.video.VideoActivity


class FeedFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(FeedFragmentViewModel::class.java) }
    lateinit var banner: ViewPager2
    lateinit var bannerAdapter: BannerAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: FeedRecyclerAdapter
    lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var mTvError: TextView
    lateinit var mIvError: ImageView


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

        bannerAdapter = BannerAdapter(viewModel.bannerData) { view, videoBean ->

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


        viewModel.getBanner()
        viewModel.banner.observe(viewLifecycleOwner) {
            viewModel.bannerData.clear()
            viewModel.bannerData.addAll(it)
            bannerAdapter.notifyDataSetChanged()
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        rvAdapter = FeedRecyclerAdapter(viewModel.videoData) { view1, videoDetail ->
            startActivity(view1, videoDetail)
        }
        recyclerView.adapter = rvAdapter
        recyclerView.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_view_fade_in
                )
            )
        swipeRefresh.isRefreshing = true
        swipeRefresh.setOnRefreshListener {
            viewModel.getFeed()
            viewModel.getBanner()
        }

        viewModel.getFeed()

        viewModel.refresh.observe(viewLifecycleOwner) {
            if (it) {
                mIvError.visibility = View.INVISIBLE
                mTvError.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
                banner.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.INVISIBLE
                banner.visibility = View.INVISIBLE
                mIvError.visibility = View.VISIBLE
                mTvError.visibility = View.VISIBLE
            }
            swipeRefresh.isRefreshing = false
        }
        viewModel.feedBean.observe(viewLifecycleOwner) {
            viewModel.videoData.clear()
            viewModel.videoData.addAll(it)
            rvAdapter.notifyItemRangeChanged(0, viewModel.videoData.size)

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FeedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FeedFragment().apply {

            }
    }
}