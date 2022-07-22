package com.example.openeye.ui.mine.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.openeye.logic.model.MessageBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.ui.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.SimpleDateFormat

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-22
 * @description
 */
class MessageActivityViewModel : BaseViewModel() {

    private val _messageBean = MutableLiveData<MessageBean>()
    val messageBean: LiveData<MessageBean>
        get() = _messageBean
    val messageData: ArrayList<MessageBean.Message> = arrayListOf()

    fun getMessage() {
        ApiService.INSTANCE.getMessage()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .safeSubscribeBy(
                onError = {
                    it.printStackTrace()
                },
                onSuccess = {
                    _messageBean.postValue(convertToMessageBean(it))
                }
            )
    }

    private fun convertToMessageBean(rawData: MessageBean): MessageBean {
        val data: ArrayList<MessageBean.Message> = arrayListOf()
        for (i in rawData.messageList) {
            i.date = SimpleDateFormat("yyyy-MM-dd HH:mm").format(i.date.toLong())
        }
        return rawData
    }
}