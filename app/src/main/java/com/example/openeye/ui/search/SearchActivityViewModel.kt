package com.example.openeye.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.logic.room.HistorySearchDatabase
import com.example.openeye.logic.room.HistorySearchEntity
import com.example.openeye.ui.base.BaseViewModel
import com.example.openeye.utils.toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchActivityViewModel : BaseViewModel() {
    private val _hotSearch = MutableLiveData<ArrayList<String>>()
    val hotSearch: MutableLiveData<ArrayList<String>>
        get() = _hotSearch

    private val _historySearch = MutableLiveData<List<HistorySearchEntity>>()
    val historySearch: MutableLiveData<List<HistorySearchEntity>>
        get() = _historySearch

    // 获取热搜
    private fun getHotSearch() {
        ApiService.INSTANCE.getHotSearch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    "请求失败了 T_T".toast()
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _hotSearch.postValue(it)
                }
            )
    }

    // 获得搜索历史记录
    private fun getHistory() {
        HistorySearchDatabase.getDatabase(appContext).historySearchDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _historySearch.postValue(it) })
    }

    // 插入搜索历史记录
    fun insertHistory(historyEntity: HistorySearchEntity) {
        HistorySearchDatabase.getDatabase(appContext).historySearchDao().insert(historyEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e(
                    "TAG", "insertHistory: $it",
                )
            })

    }

    // 删除搜索历史记录
    fun deleteHistory() {
        HistorySearchDatabase.getDatabase(appContext).historySearchDao().delete()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e(
                    "TAG", "insertHistory: $it",
                )
            })
    }

    init {
        getHotSearch()
        getHistory()
    }
}