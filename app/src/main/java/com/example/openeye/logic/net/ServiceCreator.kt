package com.example.openeye.logic.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "http://baobab.kaiyanapp.com/api/"
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)
}