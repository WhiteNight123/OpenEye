package com.example.openeye.ui.explore.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.CategoryDetailBean
import com.example.openeye.logic.model.VideoDetailData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.getTime
import com.example.openeye.utils.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-22
 * @description
 */
class CategoryDetailActivityViewModel : BaseViewModel() {
    private val _categoryDetailBean = MutableLiveData<ArrayList<VideoDetailData>>()
    val categoryDetailBean: LiveData<ArrayList<VideoDetailData>>
        get() = _categoryDetailBean
    val categoryData = ArrayList<VideoDetailData>()
    fun getCategory(id: Int) {
        ApiService.INSTANCE.getCategoryDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    "请求失败了 T_T".toast()
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _categoryDetailBean.postValue(convertToVideoDetail(it))
                }
            )
    }

    private fun convertToVideoDetail(rawData: CategoryDetailBean): ArrayList<VideoDetailData> {
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
        return data
    }
}