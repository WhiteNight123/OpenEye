package com.example.openeye.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.logic.room.HistoryEntity
import com.example.openeye.logic.room.HistorySearchDatabase
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchActivityViewModel : BaseViewModel() {
    private val _hotSearch = MutableLiveData<ArrayList<String>>()
    val hotSearch: MutableLiveData<ArrayList<String>>
        get() = _hotSearch

    private val _historySearch = MutableLiveData<List<HistoryEntity>>()
    val historySearch: MutableLiveData<List<HistoryEntity>>
        get() = _historySearch

    fun getHotSearch() {
        ApiService.INSTANCE.getHotSearch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _hotSearch.postValue(it)
                }
            )
    }

    fun getHistory() {
        HistorySearchDatabase.getDatabase(appContext).historyDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = { it.printStackTrace() },
                onSuccess = { _historySearch.postValue(it) })
    }

    fun insertHistory(historyEntity: HistoryEntity) {
        HistorySearchDatabase.getDatabase(appContext).historyDao().insert(historyEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e(
                    "TAG", "insertHistory: $it",
                )
            })

    }

    fun deleteHistory() {
        HistorySearchDatabase.getDatabase(appContext).historyDao().delete()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(onError = { it.printStackTrace() }, onSuccess = {
                Log.e(
                    "TAG", "insertHistory: $it",
                )
            })
    }


}