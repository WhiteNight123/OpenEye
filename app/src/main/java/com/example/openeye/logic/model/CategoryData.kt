package com.example.openeye.logic.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author WhiteNight(1448375249@qq.com)
 * @date 2022-07-22
 * @description
 */
data class CategoryData(
    @SerializedName("title")
    val title: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("cover")
    val cover: String?,
) : Serializable
