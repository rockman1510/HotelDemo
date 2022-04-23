package com.lastminute.hoteldemo.repository

import com.lastminute.hoteldemo.api.ApiService
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HotelListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : HotelListRepository {

    override suspend fun getHotelList(): Flow<ArrayList<Hotel>> {
        return flow {
            val response = apiService.getHotelListApi()
            response.body()?.apply { emit(ArrayList(this)) }
        }.flowOn(dispatcherProvider.IO)
    }

}