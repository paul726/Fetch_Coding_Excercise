package com.pjiang.fetch_coding_exercise.https


interface RetrofitComponent {

    fun <T> provideService(clazz: Class<T>): T
}