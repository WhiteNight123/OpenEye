package com.example.openeye.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.BannerBean
import com.example.openeye.logic.model.FeedBean
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import com.example.openeye.utils.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FeedFragmentViewModel : BaseViewModel() {
    init {
        getBanner()
        getFeed()
    }

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

    // 获取banner数据
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

    // 获取首次的feed数据
    fun getFeed(page: String = "") {
        ApiService.INSTANCE.getFeed(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    "请求失败了 T_T".toast()
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

    // 获取下一页的feed数据
    fun getNextFeed(page: String = "1658106000000") {
        ApiService.INSTANCE.getFeed(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    "请求失败了 T_T".toast()
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _feedNextBean.postValue(convertToVideoDetail(it))
                }
            )
    }


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
        // 加上下一页的url
        data[data.size - 1].nextPageUrl = rawData.nextPageUrl.subSequence(55, 68).toString()
        return data
    }

    // 转换成banner的数据
    private fun convertToBanner(rawData: BannerBean): ArrayList<VideoDetailData> {
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