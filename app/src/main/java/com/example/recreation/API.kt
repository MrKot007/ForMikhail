package com.example.recreation

import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("signIn")
    fun signIn(@Body body: ModelAuth) : ModelResponse
}