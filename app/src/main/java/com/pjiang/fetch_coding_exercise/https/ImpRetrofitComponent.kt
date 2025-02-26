package com.pjiang.fetch_coding_exercise.https

import com.pjiang.fetch_coding_exercise.constant.HOST_URL
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Single
class ImpRetrofitComponent(okHttpComponent: ImpOkHttpComponent): RetrofitComponent {

    private val retrofit = Retrofit.Builder()
        .baseUrl(HOST_URL)
        .client(okHttpComponent.okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun <T> provideService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}