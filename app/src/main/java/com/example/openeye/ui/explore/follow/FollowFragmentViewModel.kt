package com.example.openeye.ui.explore.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.FollowBean
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FollowFragmentViewModel : BaseViewModel() {
    // 获取首次的 followBean
    private val _followBean = MutableLiveData<ArrayList<VideoDetailData>>()
    val followBean: LiveData<ArrayList<VideoDetailData>>
        get() = _followBean
    val videoData = ArrayList<VideoDetailData>()
    fun getFollow(start: Int = 0) {
        ApiService.INSTANCE.getFollow(start)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _followBean.postValue(convertToVideoDetail(it))
                }
            )
    }

    private fun convertToVideoDetail(rawData: FollowBean): ArrayList<VideoDetailData> {
        val data: ArrayList<VideoDetailData> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "autoPlayFollowCard") {
                val tmp = i.data.content.data
                data.add(
                    VideoDetailData(
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