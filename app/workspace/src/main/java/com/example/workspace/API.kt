package com.example.workspace

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface API {
    @GET("workSpace/delayLessons")
    fun getExpired(@Header("Authorization") token: String) : Call<List<ModelTask>>
}