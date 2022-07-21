package com.example.openeye.logic.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class TopicData(
    @SerializedName("videoTitle")
    val videoTitle: String?,
    @SerializedName("videoId")
    val videoId: Int,
    @SerializedName("videoCover")
    val videoCover: String?,
) : Serializable