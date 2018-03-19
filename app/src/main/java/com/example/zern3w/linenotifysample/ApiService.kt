package com.example.zern3w.linenotifysample

import com.example.zern3w.linenotifysample.model.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by zern3w on 19/3/2018 AD.
 */
interface ApiService {

    @FormUrlEncoded
    @POST("notify")
    fun notify(@Field("message") message: String,
               @Header("Authorization") accessToken: String): Call<Response>

    /**
     * Companion object to create the ApiService
     */
    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://notify-api.line.me/api/")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}