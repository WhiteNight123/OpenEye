package com.example.openeye.ui.explore.topic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.TopicBean
import com.example.openeye.logic.model.TopicData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class TopicFragmentViewModel : BaseViewModel() {
    // 获取首次的 followBean
    private val _topicBean = MutableLiveData<ArrayList<TopicData>>()
    val topicBean: LiveData<ArrayList<TopicData>>
        get() = _topicBean
    val topicData = ArrayList<TopicData>()
    fun getTopic(start: Int = 0) {
        ApiService.INSTANCE.getTopic(start)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _topicBean.postValue(convertToTopic(it))
                }
            )
    }

    private fun convertToTopic(rawData: TopicBean): ArrayList<TopicData> {
        val data: ArrayList<TopicData> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "banner") {
                val tmp = i.data
                data.add(
                    TopicData(
                        tmp.title,
                        tmp.id,
                        tmp.image
                    )
                )
            }
        }
        return data
    }
}