package com.example.openeye.logic.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class VideoDetailsBean(
    val videoTitle: String,
    val videoUrl: String,
    val videoId: String,
    val videoDescription: String,
    val likeCount: Int,
    val shareCount: Int,
    val replyCount: Int,
    val authorIcon: String,
    val authorName: String,
    val authorDescription: String?,
    val videoCover: String
) : Parcelable