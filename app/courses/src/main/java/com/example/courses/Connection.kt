package com.example.courses

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object Connection {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://strukov-artemii.online:8085/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(API::class.java)
}
fun <T> Call<T>.push(onGetData: OnGetData<T>, context: Context) {
    enqueue(object: Callback<T>{
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.body() == null) {
                if (response.errorBody() != null) {
                    val modelError = Gson().fromJson<ModelError>(response.errorBody()!!.string(), ModelError::class.java)
                    onGetData.onError(modelError.error)
                }else {
                    onGetData.onError("the body is entirely empty")
                }
            }else {
                onGetData.onGet(response.body()!!)
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            Log.e("ERRROR", t.message.toString())
        }

    })
}
interface OnGetData<T> {
    fun onGet(data: T)
    fun onError(error: String)
}