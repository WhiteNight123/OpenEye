package com.example.openeye.ui.search

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.ui.video.VideoActivity
import com.example.openeye.utils.toast


private const val ARG_PARAM1 = "key"


class SearchResultFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: SearchResultRecyclerAdapter
    lateinit var mTvNoData: TextView
    lateinit var mIvNoData: ImageView
    lateinit var mLlError: ConstraintLayout
    private val viewModel by lazy { ViewModelProvider(this)[SearchResultViewModel::class.java] }
    var count = 0

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
        return inflater.inflate(R.layout.fragment_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.search_rv_result)
        mTvNoData = view.findViewById(R.id.search_tv_net_error)
        mIvNoData = view.findViewById(R.id.search_iv_net_error)
        mLlError = view.findViewById(R.id.search_constrain_layout_error)
        initRecyclerView()
        param1?.let { viewModel.getSearch(it, count) }
        viewModel.searchResult.observe(viewLifecycleOwner) {
            Log.e("TAG", "onViewCreated:$it")
            if (it.size == 0) {
                "????????????\n???????????????????????? T_T".toast()
                rvAdapter.fadeTip = true
            } else {
                rvAdapter.fadeTip = false
            }
            viewModel.videoData.addAll(it)
            rvAdapter.notifyDataSetChanged()
        }
        viewModel.refreshSuccessful.observe(viewLifecycleOwner) {
            if (!it) {
                if (viewModel.videoData.size > 1) {
                    rvAdapter.fadeTip = true
                    "???????????????".toast()
                    rvAdapter.notifyDataSetChanged()
                } else {
                    mLlError.visibility = View.VISIBLE
                    recyclerView.visibility = View.INVISIBLE
                }
            } else {
                mLlError.visibility = View.INVISIBLE
                recyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.layoutAnimation = // ????????????
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_view_fade_in
                )
            )
        rvAdapter = SearchResultRecyclerAdapter(viewModel.videoData) { view1, videoDetail ->
            startActivity(view1, videoDetail)
        }
        recyclerView.adapter = rvAdapter
        var lastVisible = 0
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                // ???newState??????????????????
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // ??????????????????footView???????????????????????????????????????????????????getItemCount???1
                    if (lastVisible + 1 == rvAdapter.itemCount) {
                        count += 10
                        viewModel.getSearch("key", count)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // ????????????????????????????????????????????????item?????????
                lastVisible = layoutManager.findLastVisibleItemPosition()
            }
        })

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