package com.example.openeye.logic

import com.example.openeye.common.unsafeSubscribeBy
import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.utils.getTime
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

object Repository {


    fun getFeed() {
        ApiService.INSTANCE.getFeed()
            .subscribeOn(Schedulers.io())  // 线程切换
            .observeOn(AndroidSchedulers.mainThread())
            .unsafeSubscribeBy(
                onError = {},
                onSuccess = {
                    if (it.itemList.isNotEmpty()) {
                        val data: ArrayList<VideoDetailsBean> = arrayListOf()
                        for (i in it.itemList) {
                            if (i.type == "followCard") {
                                val tmp = i.data.content.data
                                data.add(
                                    VideoDetailsBean(
                                        tmp.title,
                                        tmp.playUrl,
                                        i.data.id.toString(),
                                        tmp.description,
                                        tmp.consumption.collectionCount,
                                        tmp.consumption.shareCount,
                                        tmp.consumption.replyCount,
                                        tmp.author.icon,
                                        tmp.author.name,
                                        tmp.author.description, tmp.cover.feed,
                                        getTime(tmp.duration)
                                    )
                                )
                            }
                        }
                        return@unsafeSubscribeBy
                    }
                }
            )

//    suspend fun getFeed(): ArrayList<VideoDetailsBean> {
//        val response = service.getFeed().itemList
//        val data: ArrayList<VideoDetailsBean> = arrayListOf()
//        for (i in response) {
//            if (i.type == "followCard") {
//                val tmp = i.data.content.data
//                data.add(
//                    VideoDetailsBean(
//                        tmp.title,
//                        tmp.playUrl,
//                        i.data.id.toString(),
//                        tmp.description,
//                        tmp.consumption.collectionCount,
//                        tmp.consumption.shareCount,
//                        tmp.consumption.replyCount,
//                        tmp.author.icon,
//                        tmp.author.name,
//                        tmp.author.description, tmp.cover.feed
//                    )
//                )
//            }
//
//        }
//        return data
//
//    }
    }


}
