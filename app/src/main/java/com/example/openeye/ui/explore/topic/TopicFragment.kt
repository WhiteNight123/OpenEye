package com.example.openeye.ui.explore.topic

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
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
import com.example.openeye.logic.model.TopicData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopicFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this)[TopicFragmentViewModel::class.java] }

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: TopicRecyclerAdapter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.explore_rv_topic)
        swipeRefreshLayout = view.findViewById(R.id.topic_srl_refresh)
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            if (viewModel.topicData.isEmpty()) {
                viewModel.getTopic(0)
            } else {
                swipeRefreshLayout.isRefreshing = false
            }
        }
        initRecyclerView()
        viewModel.topicBean.observe(viewLifecycleOwner) {
            viewModel.topicData.addAll(it)
            swipeRefreshLayout.isRefreshing = false
            adapter.notifyDataSetChanged()
        }
        viewModel.refreshSuccess.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = false

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
        adapter = TopicRecyclerAdapter(viewModel.topicData) { view1, topicData ->
            startActivity(view1, topicData)
        }
        recyclerView.adapter = adapter

    }

    private fun startActivity(view: View, topicDetail: TopicData) {
        val intent = Intent(context, TopicDetailActivity::class.java)
        intent.putExtra("id", topicDetail.videoId)
        intent.putExtra("topicData", topicDetail)
        val options =
            ActivityOptions.makeSceneTransitionAnimation(activity, view, view.transitionName)
        startActivity(intent, options.toBundle())
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TopicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}