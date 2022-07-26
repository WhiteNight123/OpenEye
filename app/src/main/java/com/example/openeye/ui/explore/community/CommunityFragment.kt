package com.example.openeye.ui.explore.community

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.openeye.R
import com.example.openeye.logic.model.CommunityData


private const val ARG_PARAM1 = "param1"


class CommunityFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this)[CommunityFragmentViewModel::class.java] }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CommunityRecyclerAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var param1: String? = null
    var start = 0

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
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.community_rv)
        swipeRefreshLayout = view.findViewById(R.id.community_srl_refresh)
        initRecyclerView()
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            if (viewModel.communityData.size > 1) {
                swipeRefreshLayout.isRefreshing = false
            } else {
                viewModel.getCommunity(0)
            }
        }
        viewModel.communityBean.observe(viewLifecycleOwner) {
            viewModel.communityData.addAll(it)
            swipeRefreshLayout.isRefreshing = false
            Log.d("tag", "(CommunityFragment.kt:47) -> fadip:${adapter.fadeTip}")
            adapter.notifyDataSetChanged()
        }
        viewModel.refreshSuccess.observe(viewLifecycleOwner) {
            Log.d("tag", "(CommunityFragment.kt:62) -> $it")
            swipeRefreshLayout.isRefreshing = false
            adapter.fadeTip = true
            adapter.notifyDataSetChanged()
//            if (it){
//                adapter.fadeTip = true
//            }else{
//                adapter.fadeTip =
//            }
        }

    }

    private fun initRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
        recyclerView.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_view_fade_in
                )
            )
        adapter = CommunityRecyclerAdapter(viewModel.communityData) { view1, topicData ->
            startActivity(view1, topicData)
        }
        recyclerView.adapter = adapter

        var lastVisibleItem = 0
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    adapter.fadeTip = false

                    //判断是否滑动到底部
                    Log.d("tag", "(CommunityFragment.kt:98) -> isFadeTips${adapter.isFadeTips()}")
                    if (!adapter.isFadeTips() && lastVisibleItem + 1 == adapter.itemCount || lastVisibleItem + 2 == adapter.itemCount) {
                        Log.d("tag", "(CommunityFragment.kt:98) -> 滑动到底部")
                        start += 10
                        viewModel.getCommunity(start)
                    }
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //findLastVisibleItemPosition显示界面的最后一个位置
                lastVisibleItem = layoutManager.findLastVisibleItemPositions(null)[1]
            }
        })

    }

    private fun startActivity(view: View, communityData: CommunityData) {
        val intent = Intent(context, PictureDetailActivity::class.java)
        intent.putExtra("pictureData", communityData.pictureList)
        //intent.putExtra("transitionName", view.transitionName)
//        val options =
//            ActivityOptions.makeSceneTransitionAnimation(activity, view, view.transitionName)
        startActivity(intent)
    }


    companion object {

    }
}