package com.example.openeye.ui.video

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.logic.model.VideoRelevantBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class VideoActivityViewModel : BaseViewModel() {
    val videoData: ArrayList<VideoDetailsBean> = arrayListOf()

    val relevantVideo by lazy(LazyThreadSafetyMode.NONE) { MutableLiveData<ArrayList<VideoDetailsBean>>() }
    fun getVideoRelevant(id: Int) {
        ApiService.INSTANCE.getVideoRelevant(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = {
                Log.e("TAG", "getVideoRelevant: $it")
            }, onSuccess = {
                relevantVideo.postValue(toVideoDetail(it.itemList))
            })
    }

    private fun toVideoDetail(list: List<VideoRelevantBean.Item>): ArrayList<VideoDetailsBean> {
        val data: ArrayList<VideoDetailsBean> = arrayListOf()
        for (i in list) {
            if (i.type == "videoSmallCard") {
                val tmp = i.data
                if (tmp.author.icon.isNullOrEmpty()) {
                    break
                }

                data.add(
                    VideoDetailsBean(
                        tmp.title,
                        tmp.playUrl,
                        i.data.id.toString(),
                        tmp.description,
                        tmp.consumption.collectionCount,
                        tmp.consumption.shareCount,
                        tmp.consumption.replyCount,
                        tmp.author.icon,
                        tmp.author.name,
                        tmp.author.description, tmp.cover.feed,
                        getTime(tmp.duration)
                    )
                )
            }
        }
        return data
    }
}