package com.example.openeye.ui.rank

import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.RankBean
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RankDetailFragmentViewModel : BaseViewModel() {
    private val _rankData = MutableLiveData<ArrayList<VideoDetailData>>()
    val rankData: MutableLiveData<ArrayList<VideoDetailData>>
        get() = _rankData
    val videoData: ArrayList<VideoDetailData> = arrayListOf()
    fun getRank(rank: String) {
        ApiService.INSTANCE.getRank(rank).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).safeSubscribeBy(onError = {
                it.printStackTrace()
            }, onSuccess = {
                _rankData.postValue(convertToVideoDetail(it))
            })
    }

    private fun convertToVideoDetail(rawData: RankBean): ArrayList<VideoDetailData> {
        val data: ArrayList<VideoDetailData> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "video") {
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