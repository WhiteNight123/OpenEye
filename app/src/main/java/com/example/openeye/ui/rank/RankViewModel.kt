package com.example.openeye.ui.rank

import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RankViewModel : BaseViewModel() {
    private val _rankData = MutableLiveData<ArrayList<String>>()
    val rankData: MutableLiveData<ArrayList<String>>
        get() = _rankData
    val videoData: ArrayList<VideoDetailsBean> = arrayListOf()
    fun getRank(rank: String) {
        ApiService.INSTANCE.getRank(rank).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).safeSubscribeBy(onError = {
                it.printStackTrace()
            }, onSuccess = {
                // _rankData.postValue(it)
            })
    }
}