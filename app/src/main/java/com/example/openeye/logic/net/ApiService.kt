package com.example.openeye.logic.net

import com.example.openeye.logic.model.FeedBean
import retrofit2.http.GET

interface ApiService {
    @GET("v5/index/tab/feed")
    suspend fun getFeed(): FeedBean

}