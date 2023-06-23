package com.example.workspace

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface API {
    @GET("workSpace/plan")
    fun getPlan(@Query("date") date: String? = null,
                @Query("direction") direction: String? = null,
                @Header("Authorization") token: String) : Call<List<ModelTask>>
    @GET("workSpace/delayLessons")
    fun getExpired(@Header("Authorization") token: String) : Call<List<ModelTask>>
}