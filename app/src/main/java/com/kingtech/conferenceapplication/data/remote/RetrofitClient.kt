package com.kingtech.conferenceapplication.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kingtech.conferenceapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "https://conference-api-assessment.herokuapp.com/"

    private val logging = HttpLoggingInterceptor()

    private fun logLevel(): HttpLoggingInterceptor.Level {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else
            HttpLoggingInterceptor.Level.NONE
    }

    fun service(): ConferenceApi {

        //http logging
        val httpClient = OkHttpClient.Builder().addInterceptor(logging.setLevel(logLevel()))
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()

        val retrofitApi = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        return retrofitApi.create(ConferenceApi::class.java)
    }
}