package com.example.openeye.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.openeye.R
import com.example.openeye.ui.base.BaseFragmentVPAdapter
import com.example.openeye.ui.explore.category.CategoryFragment
import com.example.openeye.ui.explore.community.CommunityFragment
import com.example.openeye.ui.explore.topic.TopicFragment
import com.example.openeye.ui.widge.SquareBoxTransformer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


private const val ARG_PARAM1 = "param1"


class ExploreFragment : Fragment() {
    lateinit var mTabLayout: TabLayout
    lateinit var mViewPager2: ViewPager2
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
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTabLayout = view.findViewById(R.id.explore_tl_strategy)
        mViewPager2 = view.findViewById(R.id.explore_vp_strategy)
        initViewPager2()
        initTabLayout()

    }

    private fun initViewPager2() {
        mViewPager2.adapter = BaseFragmentVPAdapter(
            activity!!,
            listOf(
                CategoryFragment(),
                TopicFragment(),
                CommunityFragment()
            )
        )

        mViewPager2.setPageTransformer(SquareBoxTransformer())
        mViewPager2.offscreenPageLimit = 1

        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (position + positionOffset >= 0.97F) {

                }
            }
        })
    }

    // ?????? TabLayout
    private fun initTabLayout() {
        val tabs = arrayOf(
            "??????",
            "??????",
            "??????"
        )
        TabLayoutMediator(
            mTabLayout, mViewPager2
        ) { tab, position -> tab.text = tabs[position] }.attach()


    }

    companion object {

    }
}