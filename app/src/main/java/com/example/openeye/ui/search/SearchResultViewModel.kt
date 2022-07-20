package com.example.openeye.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.SearchBean
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchResultViewModel : BaseViewModel() {
    private val _searchResult = MutableLiveData<ArrayList<VideoDetailsBean>>()
    val searchResult: MutableLiveData<ArrayList<VideoDetailsBean>>
        get() = _searchResult
    private val _refreshSuccessful = MutableLiveData<Boolean>()
    val refreshSuccessful: MutableLiveData<Boolean>
        get() = _refreshSuccessful
    val videoData: ArrayList<VideoDetailsBean> = arrayListOf()
    fun getSearch(key: String, start: Int = 0) {
        ApiService.INSTANCE.getSearch(key, start)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    _refreshSuccessful.postValue(false)
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _refreshSuccessful.postValue(true)
                    _searchResult.postValue(convertToSearchResult(it))
                }
            )
    }

    private fun convertToSearchResult(rawData: SearchBean): ArrayList<VideoDetailsBean> {
        val data: ArrayList<VideoDetailsBean> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "followCard") {
                val tmp = i.data.content.data
                data.add(
                    VideoDetailsBean(
                        tmp.title,
                        tmp.playUrl,
                        tmp.id,
                        tmp.description,
                        tmp.consumption.collectionCount,
                        tmp.consumption.shareCount,
                        tmp.consumption.replyCount,
                        tmp.author?.icon,
                        tmp.author?.name,
                        tmp.author?.description, tmp.cover.feed,
                        getTime(tmp.duration),
                        null
                    )
                )
            }
        }
        return data
    }
}