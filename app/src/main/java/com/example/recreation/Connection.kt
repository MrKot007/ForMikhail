package com.example.recreation

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object Connection {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://strukov-artemii.online:8085/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(API::class.java)
}
fun <T> Call<T>.push(onGetData: OnGetData<T>, context: Context){
    enqueue(object: Callback<T>{
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.body() == null) {
                if (response.errorBody() != null) {
                    val modelError = Gson().fromJson(response.errorBody()!!.string(), ModelError::class.java)
                    onGetData.onError(modelError.error)
                }else{
                    onGetData.onError("Body null")
                }
            }else{
                onGetData.onGet(response.body()!!)
                Log.d("DATA_RECEIVED", response.body().toString())
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