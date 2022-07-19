package com.example.openeye.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.logic.model.VideoRelevantBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class VideoActivityViewModel : BaseViewModel() {
    private val _videoData = MutableLiveData<ArrayList<VideoDetailsBean>>()
    val videoData1: LiveData<ArrayList<VideoDetailsBean>>
        get() = _videoData
    private val _isFreshSuccess = MutableLiveData<Boolean>()
    val isFreshSuccess: LiveData<Boolean>
        get() = _isFreshSuccess
    val videoData: ArrayList<VideoDetailsBean> = arrayListOf()

    fun getVideoRelevant(id: Int) {
        ApiService.INSTANCE.getVideoRelevant(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                    _isFreshSuccess.postValue(false)
                },
                onSuccess = {
                    _videoData.postValue(toVideoDetail(it.itemList))
                    _isFreshSuccess.postValue(true)
                }
            )
    }

    private fun toVideoDetail(list: List<VideoRelevantBean.Item>): ArrayList<VideoDetailsBean> {
        val data: ArrayList<VideoDetailsBean> = arrayListOf()
        for (i in list) {
            if (i.type == "textCard") {
                data.add(
                    VideoDetailsBean(
                        i.data.text,
                        null,
                        i.data.id,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                )
                // 接口这里少了一个数据,自己补充了一个😜
//                if (i.data.text == "旅行相关视频") {
//                    data.add(
//                        VideoDetailsBean(
//                            "这是一个默认的填充视频",
//                            "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=8000&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss&udid=",
//                            "8000",
//                            null,
//                            null,
//                            null,
//                            null,
//                            null,
//                            "CQUPT",
//                            null,
//                            "http://hongyan.cqupt.edu.cn/assets/background.b4c69029.jpg",
//                            "13:14"
//                        )
//                    )
//                }
            }

            if (i.type == "videoSmallCard") {
                val tmp = i.data
                data.add(
                    VideoDetailsBean(
                        tmp.title,
                        tmp.playUrl,
                        i.data.id,
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