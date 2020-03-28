package com.example.vinsol_meeting_app.manager

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkManager {
    fun <T> makeRetrofitObject(serviceClass: Class<T>, baseUrl: String): T {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.interceptors().clear()
        val headerLogging = HttpLoggingInterceptor()
        headerLogging.level = HttpLoggingInterceptor.Level.HEADERS
        val bodyLogging = HttpLoggingInterceptor()
        bodyLogging.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(headerLogging)
        httpClientBuilder.addInterceptor(bodyLogging)

        val httpClient = httpClientBuilder.build()
        val retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(serviceClass)
    }
}