package com.example.openeye.logic.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 比 VideoData1 多了个tags,用于实现标签
 */
data class VideoDetailData2(
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
    var nextPageUrl: String?,
    @SerializedName("tags")
    val tags: ArrayList<String>
) : Serializable
