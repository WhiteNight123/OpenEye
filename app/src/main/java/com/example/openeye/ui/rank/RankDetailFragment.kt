package com.example.openeye.ui.rank

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.openeye.R
import com.example.openeye.ui.rank.RankDetailRvAdapter.Companion.TAG
import com.shuyu.gsyvideoplayer.GSYVideoManager

private const val ARG_PARAM1 = "param1"


class RankDetailFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(RankDetailFragmentViewModel::class.java) }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RankDetailRvAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rank_rv_detail)
        swipeRefreshLayout = view.findViewById(R.id.rank_srl_refresh)
        initRecyclerView()
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            param1?.let { viewModel.getRank(it) }
        }
        Log.e("TAG", "onViewCreated: $param1")
        param1?.let { viewModel.getRank(it) }
        viewModel.rankData.observe(viewLifecycleOwner) {
            viewModel.videoData.addAll(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.refreshSuccess.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = false
            // adapter.notifyDataSetChanged()
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
        adapter = RankDetailRvAdapter(viewModel.videoData)
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
                    if (GSYVideoManager.instance().playTag == TAG && (position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果滑出去了上面和下面就是否，和今日头条一样
                        if (!GSYVideoManager.isFullState(activity)) {
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
        if (GSYVideoManager.instance().listener() != null) {
            GSYVideoManager.instance().listener().onVideoPause()
        }
    }

    // 因为两个fragment公用一个生命周期,所以界面回来时不进行播放
    override fun onResume() {
        super.onResume()
        //GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("RankDetailFragment$param1", "onDestroy: ")
        GSYVideoManager.releaseAllVideos()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            RankDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}