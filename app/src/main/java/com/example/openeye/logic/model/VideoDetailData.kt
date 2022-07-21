package com.example.openeye.logic.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class VideoDetailData(
    @SerializedName("videoTitle")
    val videoTitle: String?,
    @SerializedName("videoUrl")
    val videoUrl: String?,
    @SerializedName("videoId")
    val videoId: Int,
    @SerializedName("videoDescription")
    val videoDescription: String?,
    @SerializedName("likeCount")
    var likeCount: Int?,
    @SerializedName("shareCount")
    val shareCount: Int?,
    @SerializedName("replyCount")
    val replyCount: Int?,
    @SerializedName("authorIcon")
    val authorIcon: String?,
    @SerializedName("authorName")
    val authorName: String?,
    @SerializedName("authorDescription")
    val authorDescription: String?,
    @SerializedName("videoCover")
    val videoCover: String?,
    @SerializedName("videoDuration")
    val videoDuration: String?,
    @SerializedName("nextPageUrl")
    var nextPageUrl: String?
) : Serializable