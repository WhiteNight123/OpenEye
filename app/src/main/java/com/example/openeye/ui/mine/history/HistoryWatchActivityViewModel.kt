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
    private val _historyWatch = MutableLiveData<List<VideoDetailData>>()
    val historyWatch: MutableLiveData<List<VideoDetailData>>
        get() = _historyWatch
    val historyWatchData: ArrayList<VideoDetailData> = arrayListOf()
    fun getHistory() {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _historyWatch.postValue(convertVideoDetailData(it)) })
    }

    private fun convertVideoDetailData(rawData: List<HistoryWatchEntity>): ArrayList<VideoDetailData> {
        var data: ArrayList<VideoDetailData> = arrayListOf()
        for (i in rawData) {
            data.add(
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

        return data
    }

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

    fun deleteHistoryVideo(videoDetailData: VideoDetailData) {
        Log.d("tag", "(HistoryWatchActivityViewModel.kt:67) -> data$videoDetailData")
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
                    Log.d("tag", "(FeedFragmentViewModel.kt:126) -> id: ${it[0].id}  ")
                    deleteHistory(convertHistoryWatchEntity2(videoDetailData, it[0].id))
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
}