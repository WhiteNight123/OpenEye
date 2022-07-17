package com.example.openeye.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.openeye.common.unsafeSubscribeBy
import com.example.openeye.logic.model.FeedBean
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class FeedFragmentViewModel : ViewModel() {
    val feedBean by lazy(LazyThreadSafetyMode.NONE) { MutableLiveData<ArrayList<VideoDetailsBean>>() }
    val mRefresh by lazy(LazyThreadSafetyMode.NONE) { MutableLiveData<Boolean>() }
    val videoData: ArrayList<VideoDetailsBean> = arrayListOf()

    fun getFeed() {
        ApiService.INSTANCE.getFeed()
            .subscribeOn(Schedulers.io())  // 线程切换
            .observeOn(AndroidSchedulers.mainThread())
            .unsafeSubscribeBy(
                onError = { mRefresh.postValue(false) },
                onSuccess = {
                    mRefresh.postValue(false)
                    if (it.itemList.isNotEmpty()) {
                        feedBean.postValue(convertToVideoDetail(it.itemList))
                    }
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