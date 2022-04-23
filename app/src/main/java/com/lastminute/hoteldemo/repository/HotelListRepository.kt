package com.lastminute.hoteldemo.repository

import com.lastminute.hoteldemo.api.model.Hotel
import kotlinx.coroutines.flow.Flow

interface HotelListRepository {
    suspend fun getHotelList(): Flow<ArrayList<Hotel>>
}