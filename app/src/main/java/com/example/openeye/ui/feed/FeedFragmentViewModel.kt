package com.example.openeye.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.FeedBean
import com.example.openeye.logic.model.RecommendBean
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.logic.room.HistoryWatchDatabase
import com.example.openeye.logic.room.HistoryWatchEntity
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FeedFragmentViewModel : BaseViewModel() {

    // banner数据
    private val _banner = MutableLiveData<ArrayList<VideoDetailData>>()
    val banner: LiveData<ArrayList<VideoDetailData>>
        get() = _banner

    // 获取首次的 feedBean
    private val _feedBean = MutableLiveData<ArrayList<VideoDetailData>>()
    val feedBean: LiveData<ArrayList<VideoDetailData>>
        get() = _feedBean

    // 获取更多的 feedBean
    private val _feedNextBean = MutableLiveData<ArrayList<VideoDetailData>>()
    val feedNextBean: LiveData<ArrayList<VideoDetailData>>
        get() = _feedNextBean

    // 刷新状态
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    // 储存的数据
    val videoData: ArrayList<VideoDetailData> = arrayListOf()
    val bannerData: ArrayList<VideoDetailData> = arrayListOf()

    fun getBanner() {
        ApiService.INSTANCE.getBanner().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    _banner.postValue(convertToBanner(it))
                }
            )
    }

    fun getFeed(page: String = "") {
        ApiService.INSTANCE.getFeed(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                    _refresh.postValue(false)
                },
                onSuccess = {
                    _refresh.postValue(true)
                    Log.d("TAG", "getFeed: $it")
                    _feedBean.postValue(convertToVideoDetail(it))
                }
            )
    }

    fun getNextFeed(page: String = "1658106000000") {
        ApiService.INSTANCE.getFeed(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _feedNextBean.postValue(convertToVideoDetail(it))
                }
            )
    }

    fun insertHistory(videoDetailData: VideoDetailData) {
        HistoryWatchDatabase.getDatabase(appContext).historyWatchDao()
            .insert(convertHistoryWatchEntity(videoDetailData))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e(
                    "TAG", "insertHistory: $it",
                )
            })

    }

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


    // 转换一下返回的数据,这接口给个太乱了😒
    private fun convertToVideoDetail(rawData: FeedBean): ArrayList<VideoDetailData> {
        val data: ArrayList<VideoDetailData> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "followCard") {
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
        // 下一页的url
        data[data.size - 1].nextPageUrl = rawData.nextPageUrl.subSequence(55, 68).toString()
        return data
    }

    private fun convertToBanner(rawData: RecommendBean): ArrayList<VideoDetailData> {
        val data: ArrayList<VideoDetailData> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "followCard") {
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
            } else if (i.type == "videoSmallCard") {
                val tmp = i.data
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
                        tmp.author?.description,
                        tmp.cover.feed,
                        getTime(tmp.duration),
                        null
                    )
                )
            }
        }
        // 第一个位置加上最后一张图,最后一个位置加上第一张图,
        data.add(0, data[data.size - 1])
        data.add(data[1])
        return data
    }
}