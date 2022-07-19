package com.example.openeye.logic.net

import com.example.openeye.logic.model.FeedBean
import com.example.openeye.logic.model.FeedBean1
import com.example.openeye.logic.model.VideoRelevantBean
import com.ndhzs.lib.common.network.ApiGenerator
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://baobab.kaiyanapp.com/api/"
interface ApiService {
    @GET("v5/index/tab/feed?num=1")
    fun getFeed(@Query("date") date: String): Single<FeedBean>

    @GET("v4/video/related")
    fun getVideoRelevant(@Query("id") videoId: Int): Single<VideoRelevantBean>

    @GET("v5/index/tab/allRec?page=0")
    fun getBanner(): Single<FeedBean1>

    @GET("/v3/queries/hot")
    fun getHotSearch(): Single<ArrayList<String>>

    companion object {
        val INSTANCE by lazy {
            ApiGenerator.getApiService(ApiService::class)
        }
    }
}