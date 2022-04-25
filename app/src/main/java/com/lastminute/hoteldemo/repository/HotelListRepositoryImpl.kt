package com.lastminute.hoteldemo.repository

import com.lastminute.hoteldemo.api.ApiService
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.base.CoroutinesDispatcherProvider
import com.lastminute.hoteldemo.roomdatabase.AppDataBase
import com.lastminute.hoteldemo.roomdatabase.entity.HotelFilterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HotelListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val roomDataBase: AppDataBase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : HotelListRepository {

    override suspend fun getHotelList(): Flow<ArrayList<Hotel>> {
        return flow {
            val response = apiService.getHotelListApi()
            response.body()?.apply { emit(ArrayList(this)) }
        }.flowOn(dispatcherProvider.IO)
    }

    override suspend fun getHotelFilterLocalData(): Flow<HotelFilterEntity> {
        return flow {
            emit(roomDataBase.getHotelFilterDAO().getHotelFilterEntity())
        }.flowOn(dispatcherProvider.IO)
    }

    override suspend fun updateHotelFilterLocalData(hotelFilterEntity: HotelFilterEntity) {
        withContext(dispatcherProvider.IO) {
            roomDataBase.getHotelFilterDAO().updateHotelFilterEntity(hotelFilterEntity)
        }
    }
}