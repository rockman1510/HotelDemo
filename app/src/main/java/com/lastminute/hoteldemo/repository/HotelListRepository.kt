package com.lastminute.hoteldemo.repository

import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.roomdatabase.entity.HotelFilterEntity
import kotlinx.coroutines.flow.Flow

interface HotelListRepository {
    suspend fun getHotelList(): Flow<ArrayList<Hotel>>
    suspend fun getHotelFilterLocalData(): Flow<HotelFilterEntity>
    suspend fun updateHotelFilterLocalData(hotelFilterEntity: HotelFilterEntity)
}