package com.pjiang.fetch_coding_exercise.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pjiang.fetch_coding_exercise.entities.HiringEntity
import com.pjiang.fetch_coding_exercise.repository.HiringRepository
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HiringViewModel(private val hiringRepository: HiringRepository): ViewModel() {

    val hiringMutableLiveData = MutableLiveData<Map<Int, List<HiringEntity>>>()
    val errorMsgLiveData = MutableLiveData<String>()


    fun fetchHiringData() {
        viewModelScope.launch {
            hiringRepository.fetch().collect { hiringApiResult ->
                if (hiringApiResult.data == null) {
                    //error occurred
                    hiringApiResult.message?.apply {
                        errorMsgLiveData.value = this
                    }
                } else {
                    val hiringMap = hiringApiResult.data
                        .filterNotNull()
                        .filter { hiringEntity -> hiringEntity.name?.isNotBlank() == true }
                        .groupBy { it.listId }
                        .mapValues { it.value.sortedBy { it.name?.split(" ")?.get(1)?.toInt() } }
                        .toSortedMap()
                    hiringMutableLiveData.value = hiringMap

                }
            }
        }
    }
}