package com.example.openeye.ui.explore.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.openeye.R


private const val ARG_PARAM1 = "param1"


class CommunityFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this)[CommunityFragmentViewModel::class.java] }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CommunityRecyclerAdapter
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
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.community_rv)
        initRecyclerView()
        viewModel.getCommunity()
        viewModel.communityBean.observe(viewLifecycleOwner) {
            viewModel.communityData.clear()
            viewModel.communityData.addAll(it)
            adapter.notifyDataSetChanged()
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
            //startActivity(view1, topicData)
        }
        recyclerView.adapter = adapter

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommunityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}