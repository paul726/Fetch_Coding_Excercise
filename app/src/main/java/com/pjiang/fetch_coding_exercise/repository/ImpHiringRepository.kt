package com.pjiang.fetch_coding_exercise.repository

import com.pjiang.fetch_coding_exercise.entities.HiringEntity
import com.pjiang.fetch_coding_exercise.https.ApiResult
import com.pjiang.fetch_coding_exercise.https.RetrofitComponent
import com.pjiang.fetch_coding_exercise.https.service.HiringService
import com.pjiang.fetch_coding_exercise.https.toFlow
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class ImpHiringRepository(private val retrofitComponent: RetrofitComponent): HiringRepository {

    override suspend fun fetch(): Flow<ApiResult<List<HiringEntity?>>> {
        return toFlow {
            retrofitComponent.provideService(HiringService::class.java).getHiringData()
        }
    }
}