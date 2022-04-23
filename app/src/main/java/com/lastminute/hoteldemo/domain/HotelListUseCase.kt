package com.lastminute.hoteldemo.domain

import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.base.CoroutinesDispatcherProvider
import com.lastminute.hoteldemo.repository.HotelListRepository
import javax.inject.Inject

class HotelListUseCase @Inject constructor(
    val hotelListRepository: HotelListRepository,
    val dispatcherProvider: CoroutinesDispatcherProvider
) {
    suspend operator fun invoke(): ArrayList<Hotel> {
        TODO()
    }
}