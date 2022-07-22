package com.example.openeye.ui.explore.topic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.TopicDetailBean
import com.example.openeye.logic.model.VideoDetailData2
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TopicDetailActivityViewModel : BaseViewModel() {

    private val _topicDetailBean = MutableLiveData<ArrayList<VideoDetailData2>>()
    val topicDetailBean: LiveData<ArrayList<VideoDetailData2>>
        get() = _topicDetailBean
    val topicDetailData = ArrayList<VideoDetailData2>()
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

    private fun convertToTopic(rawData: TopicDetailBean): ArrayList<VideoDetailData2> {
        val data: ArrayList<VideoDetailData2> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "autoPlayFollowCard") {
                val tmp = i.data.content.data
                val tags: ArrayList<String> = arrayListOf()
                for (j in i.data.content.data.tags) {
                    tags.add(j.name)
                }
                data.add(
                    VideoDetailData2(
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
                        tmp.releaseTime.toString(),
                        null,
                        tags
                    )
                )
            }
        }
        // 这个是引言
        data[0].nextPageUrl = rawData.text
        return data
    }
}