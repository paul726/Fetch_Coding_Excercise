package com.pjiang.fetch_coding_exercise.repository

import com.pjiang.fetch_coding_exercise.entities.HiringEntity
import com.pjiang.fetch_coding_exercise.https.ApiResult
import kotlinx.coroutines.flow.Flow

interface HiringRepository {

    suspend fun fetch(): Flow<ApiResult<List<HiringEntity?>>>
}