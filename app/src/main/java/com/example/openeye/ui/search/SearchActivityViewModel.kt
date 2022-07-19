package com.example.openeye.ui.search

import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchActivityViewModel : BaseViewModel() {
    private val _hotSearch = MutableLiveData<ArrayList<String>>()
    val hotSearch: MutableLiveData<ArrayList<String>>
        get() = _hotSearch

    fun getHotSearch() {
        ApiService.INSTANCE.getHotSearch().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                }, onSuccess = {
                    _hotSearch.postValue(it)
                })
    }

}