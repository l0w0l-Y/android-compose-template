package com.kaleksandra.coredata.network

import com.kaleksandra.coredata.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.Builder.client(interceptors: List<Interceptor>? = null): Retrofit.Builder {
    return this.client(
        OkHttpClient.Builder().apply {
            interceptors?.forEach {
                addInterceptor(it)
            }
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.build()
    )
}

fun builder(baseUrl: String = BuildConfig.BASE_URL): Retrofit.Builder {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
}