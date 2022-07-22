package com.example.openeye.ui.explore.category

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.CategoryBean
import com.example.openeye.logic.model.CategoryData
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-22
 * @description
 */
class CategoryFragmentViewModel : BaseViewModel() {
    private val _categoryBean = MutableLiveData<ArrayList<CategoryData>>()
    val categoryBean: LiveData<ArrayList<CategoryData>>
        get() = _categoryBean
    val categoryData = ArrayList<CategoryData>()
    fun getCategory() {
        ApiService.INSTANCE.getCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    Log.d("TAG", "getFeed: $it")
                    _categoryBean.postValue(convertToVideoDetail(it))
                }
            )
    }

    private fun convertToVideoDetail(rawData: CategoryBean): ArrayList<CategoryData> {
        val data: ArrayList<CategoryData> = arrayListOf()
        for (i in rawData.itemList) {
            if (i.type == "squareCard" && i.data.id > 1) {
                data.add(
                    CategoryData(
                        i.data.title,
                        i.data.id,
                        i.data.image
                    )
                )
            }
        }
        return data
    }
}