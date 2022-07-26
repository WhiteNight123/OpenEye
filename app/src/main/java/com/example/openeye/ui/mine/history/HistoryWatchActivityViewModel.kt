package com.example.openeye.ui.mine.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.logic.room.HistoryWatchDatabase
import com.example.openeye.logic.room.HistoryWatchEntity
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-22
 * @description
 */
class HistoryWatchActivityViewModel : BaseViewModel() {
    init {
        getHistory()
    }

    // 历史观看记录
    private val _historyWatch = MutableLiveData<List<VideoDetailData>>()
    val historyWatch: MutableLiveData<List<VideoDetailData>>
        get() = _historyWatch

    // 储存的历史记录
    val historyWatchData: ArrayList<VideoDetailData> = arrayListOf()

    //获取全部历史记录
    fun getHistory() {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _historyWatch.postValue(convertVideoDetailData(it)) })
    }

    // 删除所有历史记录(删库)
    fun deleteAllHistory() {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao().deleteAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e(
                    "TAG", "insertHistory: $it",
                )
            })
    }


    // 删除某条历史记录(先查找)
    fun deleteHistoryVideo(videoDetailData: VideoDetailData) {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao()
            .findHistoryWatchVideo(convertHistoryWatchEntity(videoDetailData).videoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e("TAG", "insertHistory: $it")
                if (it.isEmpty()) {
                    Log.d("tag", "(HistoryWatchActivityViewModel.kt:76) -> no data")
                } else {
                    Log.d("tag", "(FeedFragmentViewModel.kt:126) -> list: $it")
                    deleteHistory(convertHistoryWatchEntity2(videoDetailData, it[0].id))
                }
            })
    }

    // 删除某条历史记录(再删除)
    private fun deleteHistory(historyWatchEntity: HistoryWatchEntity) {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao().delete(historyWatchEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e(
                    "TAG", "insertHistory: $it",
                )
            })
    }

    // 转换成videoDetailData
    private fun convertVideoDetailData(rawData: List<HistoryWatchEntity>): ArrayList<VideoDetailData> {
        var data1: ArrayList<VideoDetailData> = arrayListOf()
        for (i in rawData) {
            data1.add(
                VideoDetailData(
                    i.videoTitle,
                    i.videoUrl,
                    i.videoId,
                    i.videoDescription,
                    i.likeCount,
                    i.shareCount,
                    i.replyCount,
                    i.authorIcon,
                    i.authorName,
                    i.authorDescription, i.videoCover,
                    i.videoDuration,
                    SimpleDateFormat("yyyy-MM-dd HH:mm").format(i.nextPageUrl?.toLong())
                )
            )
        }

        return data1
    }

    // 转换成Room的数据类
    // 主要是我前期的数据类使用不规范,后期不想改了😭,只能自己手动转换...
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

    // 转换成Room的数据类(增加了主键)
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

}