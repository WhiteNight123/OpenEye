package com.example.openeye.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class BaseFragmentVPAdapter<T>(
    fragmentActivity: FragmentActivity,
    private var fragmentList: List<T>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position] as Fragment
    }
}