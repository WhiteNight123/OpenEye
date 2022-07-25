package com.example.openeye.logic.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-23
 * @description
 */
data class CommunityData(
    @SerializedName("pictureId")
    val pictureId: Int,
    @SerializedName("pictureDescription")
    val pictureDescription: String?,
    @SerializedName("likeCount")
    var likeCount: Int?,
    @SerializedName("authorIcon")
    val authorIcon: String?,
    @SerializedName("authorName")
    val authorName: String?,
    @SerializedName("videoCover")
    val videoCover: String?,
    @SerializedName("nextPageUrl")
    var nextPageUrl: String?,
    @SerializedName("pictureList")
    val pictureList: ArrayList<String>
) : Serializable
