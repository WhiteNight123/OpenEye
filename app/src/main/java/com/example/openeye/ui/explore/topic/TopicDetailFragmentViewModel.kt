package com.example.openeye.ui.explore.topic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.TopicDetailBean
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TopicDetailFragmentViewModel : BaseViewModel() {

    private val _topicDetailBean = MutableLiveData<ArrayList<VideoDetailData>>()
    val topicDetailBean: LiveData<ArrayList<VideoDetailData>>
        get() = _topicDetailBean
    val topicDetailData = ArrayList<VideoDetailData>()
    fun getDetailTopic(start: Int = 0) {
        ApiService.INSTANCE.getTopicDetail(start)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _topicDetailBean.postValue(convertToTopic(it))
                }
            )
    }

    private fun convertToTopic(rawData: TopicDetailBean): ArrayList<VideoDetailData> {
        val data: ArrayList<VideoDetailData> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "autoPlayFollowCard") {
                val tmp = i.data.content.data
                data.add(
                    VideoDetailData(
                        tmp.title,
                        tmp.playUrl,
                        tmp.id,
                        tmp.releaseTime.toString(),
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