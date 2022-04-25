package com.lastminute.hoteldemo.repository

import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.base.CoroutinesDispatcherProvider
import com.lastminute.hoteldemo.roomdatabase.entity.HotelFilterEntity
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject
import com.lastminute.hoteldemo.utils.HotelFilterUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class FakeHotelListRepositoryImpl(
    private val dataList: ArrayList<Hotel>,
    private val hotelFilterObject: HotelFilterObject,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : HotelListRepository {
    override suspend fun getHotelList(): Flow<ArrayList<Hotel>> {
        return flow {
            emit(dataList)
        }.flowOn(dispatcherProvider.IO)
    }

    override suspend fun getHotelFilterLocalData(): Flow<HotelFilterEntity> {
        return flow {
            emit(HotelFilterUtils.fromObjectToEntity(hotelFilterObject))
        }.flowOn(dispatcherProvider.IO)
    }

    override suspend fun updateHotelFilterLocalData(hotelFilterEntity: HotelFilterEntity) {
        withContext(dispatcherProvider.IO) {
            //TODO
        }
    }
}