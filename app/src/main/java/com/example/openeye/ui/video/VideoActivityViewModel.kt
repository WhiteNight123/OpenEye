package com.example.openeye.ui.video

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.logic.model.VideoRelevantBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.logic.room.HistoryWatchDatabase
import com.example.openeye.logic.room.HistoryWatchEntity
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class VideoActivityViewModel : BaseViewModel() {
    private val _videoData = MutableLiveData<ArrayList<VideoDetailData>>()
    val videoData1: LiveData<ArrayList<VideoDetailData>>
        get() = _videoData
    private val _isFreshSuccess = MutableLiveData<Boolean>()
    val isFreshSuccess: LiveData<Boolean>
        get() = _isFreshSuccess
    val videoData: ArrayList<VideoDetailData> = arrayListOf()

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

    // 添加数据
    private fun insertHistory(videoDetailData: VideoDetailData) {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao()
            .insert(convertHistoryWatchEntity(videoDetailData))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.d("tag", "(FeedFragmentViewModel.kt:113) -> insert:")

            })

    }

    //更新数据
    private fun updateHistory(videoHistoryWatchEntity: HistoryWatchEntity) {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao()
            .update(videoHistoryWatchEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = { Log.d("tag", "(FeedFragmentViewModel.kt:110) -> $it") },
                onSuccess = {
                    Log.d("tag", "(FeedFragmentViewModel.kt:113) -> update:")
                })

    }

    // 添加数据,如果存在就更新,如果不存在就插入
    fun addHistoryVideo(videoDetailData: VideoDetailData) {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao()
            .findHistoryWatchVideo(convertHistoryWatchEntity(videoDetailData).videoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e("TAG", "insertHistory: $it")
                if (it.isEmpty()) {
                    insertHistory(videoDetailData)
                } else {
                    Log.d("tag", "(FeedFragmentViewModel.kt:126) -> list: $it")
                    Log.d("tag", "(FeedFragmentViewModel.kt:126) -> id: ${it[0].id}  ")
                    updateHistory(convertHistoryWatchEntity2(videoDetailData, it[0].id))
                }
            })
    }

    // 转换成Room的数据类
    private fun convertHistoryWatchEntity(rawData: VideoDetailData) = HistoryWatchEntity(
        rawData.videoTitle,
        rawData.videoUrl,
        rawData.videoId,
        rawData.videoDescription,
        rawData.likeCount,
        rawData.shareCount,
        rawData.replyCount,
        rawData.authorIcon,
        rawData.authorName,
        rawData.authorDescription,
        rawData.videoCover,
        rawData.videoDuration,
        System.currentTimeMillis().toString()
    )

    // 增加了主键
    private fun convertHistoryWatchEntity2(rawData: VideoDetailData, id: Long): HistoryWatchEntity {
        val a = HistoryWatchEntity(
            rawData.videoTitle,
            rawData.videoUrl,
            rawData.videoId,
            rawData.videoDescription,
            rawData.likeCount,
            rawData.shareCount,
            rawData.replyCount,
            rawData.authorIcon,
            rawData.authorName,
            rawData.authorDescription,
            rawData.videoCover,
            rawData.videoDuration,
            System.currentTimeMillis().toString()
        )
        a.id = id
        return a
    }


    private fun toVideoDetail(list: List<VideoRelevantBean.Item>): ArrayList<VideoDetailData> {
        val data: ArrayList<VideoDetailData> = arrayListOf()
        for (i in list) {
            if (i.type == "textCard") {
                data.add(
                    VideoDetailData(
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
                    VideoDetailData(
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