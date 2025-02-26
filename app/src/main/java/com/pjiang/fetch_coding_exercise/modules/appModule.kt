package com.pjiang.fetch_coding_exercise.modules

import com.pjiang.fetch_coding_exercise.https.ImpOkHttpComponent
import com.pjiang.fetch_coding_exercise.https.ImpRetrofitComponent
import com.pjiang.fetch_coding_exercise.https.OkHttpComponent
import com.pjiang.fetch_coding_exercise.https.RetrofitComponent
import com.pjiang.fetch_coding_exercise.repository.HiringRepository
import com.pjiang.fetch_coding_exercise.repository.ImpHiringRepository
import com.pjiang.fetch_coding_exercise.viewmodel.HiringViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    singleOf(::ImpOkHttpComponent) { bind<OkHttpComponent>()}
    singleOf(::ImpRetrofitComponent) { bind<RetrofitComponent>()}
    singleOf(::ImpHiringRepository) { bind<HiringRepository>()}
    viewModel{ HiringViewModel(get()) }
}