package com.pjiang.fetch_coding_exercise.https

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Single
import java.util.concurrent.TimeUnit

@Single
class ImpOkHttpComponent : OkHttpComponent {

    private val TIME_OUT_SECONDS = 30

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request =
                it.request().newBuilder().addHeader("content-type", "application/json").build()
            it.proceed(request)
        }
        .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
        .readTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level =  HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}