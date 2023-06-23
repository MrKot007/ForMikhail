package com.example.courses

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface API {
    @GET("catalog/tags")
    fun getTags() : Call<List<ModelTag>>
    @GET("catalog/courses")
    fun getCourses(@Header("Authorization") token: String) : Call<List<ModelCourse>>
    @GET("catalog/course")
    fun getCourse(@Query("idCourse") idCourse: Int): Call<CourseData>
}