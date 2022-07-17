package com.example.openeye.logic.net

import com.example.openeye.logic.model.FeedBean
import com.example.openeye.logic.model.VideoRelevantBean
import com.ndhzs.lib.common.network.ApiGenerator
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://baobab.kaiyanapp.com/api/"
interface ApiService {
    @GET("v5/index/tab/feed")
    fun getFeed(): Single<FeedBean>

    @GET("v4/video/related")
    fun getVideoRelevant(@Query("id") page: Int): Single<VideoRelevantBean>

    companion object {
        val INSTANCE by lazy {
            ApiGenerator.getApiService(ApiService::class)
        }
    }
}