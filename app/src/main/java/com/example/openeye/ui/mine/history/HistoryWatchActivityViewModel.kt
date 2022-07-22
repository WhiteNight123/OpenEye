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
        val data: ArrayList<VideoDetailData> = arrayListOf()
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


    fun deleteHistory() {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao().delete()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e(
                    "TAG", "insertHistory: $it",
                )
            })
    }
}