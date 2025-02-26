package com.pjiang.fetch_coding_exercise.https.service

import com.pjiang.fetch_coding_exercise.entities.HiringEntity
import retrofit2.Response
import retrofit2.http.GET

interface HiringService {

    @GET("hiring.json")
    suspend fun getHiringData(): Response<List<HiringEntity?>>
}