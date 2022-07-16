package com.example.openeye.logic

import com.example.openeye.logic.model.VideoDetailsBean
import com.example.openeye.logic.net.ApiService
import com.example.openeye.logic.net.ServiceCreator

object Repository {
    val service = ServiceCreator.create(ApiService::class.java)
    suspend fun getFeed(): ArrayList<VideoDetailsBean> {
        val response = service.getFeed().itemList
        val data: ArrayList<VideoDetailsBean> = arrayListOf()
        for (i in response) {
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
                        tmp.author.description, tmp.cover.feed
                    )
                )
            }

        }
        return data

    }
}