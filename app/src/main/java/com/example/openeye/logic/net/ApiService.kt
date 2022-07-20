package com.example.openeye.logic.net

import com.example.openeye.logic.model.*
import com.ndhzs.lib.common.network.ApiGenerator
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "http://baobab.kaiyanapp.com/api/"
interface ApiService {
    // 获取日报
    @GET("v5/index/tab/feed?num=1")
    fun getFeed(@Query("date") date: String): Single<FeedBean>

    // 获取视频相关推荐
    @GET("v4/video/related")
    fun getVideoRelevant(@Query("id") videoId: Int): Single<VideoRelevantBean>

    // 获取banner
    @GET("v5/index/tab/allRec?page=0")
    fun getBanner(): Single<BannerBean>

    // 获取热搜
    @GET("v3/queries/hot")
    fun getHotSearch(): Single<HotSearchBean>

    // 搜索
    @GET("v3/search")
    fun getSearch(@Query("query") query: String, @Query("start") start: Int): Single<SearchBean>

    // 获取排行
    @GET("v4/rankList/videos")
    fun getRank(@Query("strategy") strategy: String): Single<RankBean>


    companion object {
        val INSTANCE by lazy {
            ApiGenerator.getApiService(ApiService::class)
        }
    }
}