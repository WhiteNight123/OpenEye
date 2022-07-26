package com.example.openeye.ui.explore.category

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.openeye.R
import com.example.openeye.logic.model.CategoryData


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CategoryFragment : Fragment() {
    private val viewModel by lazy { ViewModelProvider(this)[CategoryFragmentViewModel::class.java] }
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CategoryRecyclerAdapter
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        recyclerView = view.findViewById(R.id.category_rv)
        swipeRefreshLayout = view.findViewById(R.id.category_srl_refresh)
        initRecyclerView()
        swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getCategory()
        }
        viewModel.getCategory()
        viewModel.categoryBean.observe(viewLifecycleOwner) {
            viewModel.categoryData.addAll(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.refreshSuccess.observe(viewLifecycleOwner) {
            swipeRefreshLayout.isRefreshing = false
            adapter.notifyDataSetChanged()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.layoutAnimation = // 入场动画
            LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.recycler_view_fade_in
                )
            )
        adapter = CategoryRecyclerAdapter(viewModel.categoryData) { view1, categoryDetail ->
            startActivity(view1, categoryDetail)
        }
        recyclerView.adapter = adapter

    }

    private fun startActivity(view: View, categoryData: CategoryData) {
        val intent = Intent(context, CategoryDetailActivity::class.java)
        intent.putExtra("categoryData", categoryData)
        val options =
            ActivityOptions.makeSceneTransitionAnimation(activity, view, view.transitionName)
        startActivity(intent, options.toBundle())
    }

    companion object {

    }
}