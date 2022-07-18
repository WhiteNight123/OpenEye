package com.example.openeye.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.FeedBean
import com.example.openeye.logic.model.RecommendBean
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FeedFragmentViewModel : BaseViewModel() {

    private val _feedBean = MutableLiveData<ArrayList<VideoDetailsBean>>()
    val feedBean: LiveData<ArrayList<VideoDetailsBean>>
        get() = _feedBean
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh
    val videoData: ArrayList<VideoDetailsBean> = arrayListOf()

    private val _banner = MutableLiveData<ArrayList<VideoDetailsBean>>()
    val banner: LiveData<ArrayList<VideoDetailsBean>>
        get() = _banner
    val bannerData: ArrayList<VideoDetailsBean> = arrayListOf()

    fun getBanner() {
        ApiService.INSTANCE.getBanner().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    _banner.postValue(convertToBanner(it.itemList))
                }
            )
    }

    fun getFeed() {
        ApiService.INSTANCE.getFeed()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                    _refresh.postValue(false)
                },
                onSuccess = {
                    _refresh.postValue(true)
                    _feedBean.postValue(convertToVideoDetail(it.itemList))
                }
            )
    }

    // 转换一下返回的数据,这接口给个太乱了😒
    private fun convertToVideoDetail(list: ArrayList<FeedBean.Item>): ArrayList<VideoDetailsBean> {
        val data: ArrayList<VideoDetailsBean> = arrayListOf()
        for (i in list) {
            if (i.type == "followCard") {
                val tmp = i.data.content.data
                data.add(
                    VideoDetailsBean(
                        tmp.title,
                        tmp.playUrl,
                        tmp.id,
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

    private fun convertToBanner(list: ArrayList<RecommendBean.Item>): ArrayList<VideoDetailsBean> {
        val data: ArrayList<VideoDetailsBean> = arrayListOf()

        for (i in list[0].data.itemList) {
            if (i.type == "followCard") {
                val tmp = i.data.content.data
                data.add(
                    VideoDetailsBean(
                        tmp.title,
                        tmp.playUrl,
                        tmp.id,
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
        data.add(0, data[data.size - 1])
        data.add(data[1])
        return data
    }
}