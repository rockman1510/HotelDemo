package com.lastminute.hoteldemo.di.module

import com.lastminute.hoteldemo.domain.HotelListUseCase
import com.lastminute.hoteldemo.repository.HotelListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideHotelListUseCase(
        hotelListRepository: HotelListRepository
    ): HotelListUseCase = HotelListUseCase(hotelListRepository)
}