package com.example.openeye.ui.explore.community

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.CommunityBean
import com.example.openeye.logic.model.CommunityData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-23
 * @description
 */
class CommunityFragmentViewModel : BaseViewModel() {
    // 获取首次的 followBean
    private val _communityBean = MutableLiveData<ArrayList<CommunityData>>()
    val communityBean: LiveData<ArrayList<CommunityData>>
        get() = _communityBean
    val communityData = ArrayList<CommunityData>()
    fun getCommunity() {
        ApiService.INSTANCE.getCommunity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _communityBean.postValue(convertToTopic(it))
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
                        null
                    )
                )
            }
        }
        return data
    }

    private fun convertToDefaultTitle(title: String) = if (title == "") "这是默认标题" else title
}