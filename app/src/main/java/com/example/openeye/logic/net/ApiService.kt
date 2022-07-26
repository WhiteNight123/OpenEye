package com.example.openeye.logic.net

import com.example.openeye.logic.model.*
import com.ndhzs.lib.common.network.ApiGenerator
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
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

    // 获取专题
    @GET("v3/specialTopics?vc=591&deviceModel=Che1-CL20&num=10")
    fun getTopic(@Query("start") start: Int): Single<TopicBean>

    // 获取专题详细
    @GET("v3/lightTopics/internal/{id}")
    fun getTopicDetail(@Path("id") id: Int): Single<TopicDetailBean>

    // 获取分类
    @GET("v4/categories/all")
    fun getCategory(): Single<CategoryBean>

    // 获取分类详情
    @GET("v5/index/tab/category/{id}?udid=435865baacfc49499632ea13c5a78f944c2f28aa")
    fun getCategoryDetail(@Path("id") id: Int): Single<CategoryDetailBean>


    // 获取社区
    @GET("v5/index/tab/ugcSelected")
    fun getCommunity(@Query("start") start: Int): Single<CommunityBean>

    companion object {
        val INSTANCE by lazy {
            ApiGenerator.getApiService(ApiService::class)
        }
    }
}