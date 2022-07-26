package com.example.openeye.ui.explore.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.CommunityBean
import com.example.openeye.logic.model.CommunityData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-23
 * @description
 */
class CommunityFragmentViewModel : BaseViewModel() {
    init {
        getCommunity()
    }

    // 获取首次的 communityBean
    private val _communityBean = MutableLiveData<ArrayList<CommunityData>>()
    val communityBean: LiveData<ArrayList<CommunityData>>
        get() = _communityBean
    val communityData = ArrayList<CommunityData>()
    private val _refreshSuccess = MutableLiveData<Boolean>()
    val refreshSuccess: LiveData<Boolean>
        get() = _refreshSuccess

    fun getCommunity(start: Int = 0) {
        ApiService.INSTANCE.getCommunity(start)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    "请求失败了 T_T".toast()
                    _refreshSuccess.postValue(false)
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    if (it != null) {
                        _refreshSuccess.postValue(true)
                        _communityBean.postValue(convertToTopic(it))
                    } else {
                        _refreshSuccess.postValue(false)
                    }
                }
            )
    }

    private fun convertToTopic(rawData: CommunityBean): ArrayList<CommunityData> {
        val data: ArrayList<CommunityData> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "pictureFollowCard") {
                val tmp = i.data.content.data
                data.add(
                    CommunityData(
                        tmp.id,
                        convertToDefaultTitle(tmp.description),
                        tmp.consumption.collectionCount,
                        tmp.owner.avatar,
                        tmp.owner.nickname,
                        tmp.cover.feed,
                        null,
                        tmp.urls
                    )
                )
            }
        }
        return data
    }

    private fun convertToDefaultTitle(title: String) = if (title == "") "这是默认标题" else title
}