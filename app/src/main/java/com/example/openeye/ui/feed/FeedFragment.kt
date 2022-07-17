package com.example.openeye.ui.feed

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.openeye.R
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.ui.video.VideoActivity


class FeedFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this).get(FeedFragmentViewModel::class.java) }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FeedRecyclerAdapter
    lateinit var swipeRefresh: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.feed_rv)
        swipeRefresh = view.findViewById(R.id.feed_srl_refresh)

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FeedRecyclerAdapter(viewModel.videoData) { view1, videoDetail ->
            startActivity(view1, videoDetail)
        }
        recyclerView.adapter = adapter
        swipeRefresh.isRefreshing = true
        swipeRefresh.setOnRefreshListener { viewModel.getFeed() }

        viewModel.mRefresh.observe(viewLifecycleOwner) {
            swipeRefresh.isRefreshing = it
        }
        viewModel.feedBean.observe(viewLifecycleOwner) {
            viewModel.videoData.clear()
            viewModel.videoData.addAll(it)
            adapter.notifyItemRangeChanged(0, viewModel.videoData.size)
        }
        viewModel.getFeed()

    }

    private fun startActivity(view: View, videoDetail: VideoDetailsBean) {
        val intent = Intent(context, VideoActivity::class.java)
        intent.putExtra("videoDetail", videoDetail)
        val options = ActivityOptions.makeSceneTransitionAnimation(activity, view, "video_cover")
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