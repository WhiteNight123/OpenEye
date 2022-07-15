package com.example.openeye

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragmentPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val mFragment: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return mFragment[position]
    }

    override fun getItemCount(): Int {
        return mFragment.size
    }

}