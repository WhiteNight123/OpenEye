package com.example.openeye.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeye.logic.Repository
import com.example.openeye.logic.model.VideoDetailsBean

class FeedFragmentViewModel : ViewModel() {
    val feedBean by lazy(LazyThreadSafetyMode.NONE) { MutableLiveData<ArrayList<VideoDetailsBean>>() }
    val mRefresh by lazy(LazyThreadSafetyMode.NONE) { MutableLiveData<Boolean>() }

    suspend fun getFeed() {
        mRefresh.postValue(true)
        feedBean.postValue(Repository.getFeed())
    }
}