package com.lastminute.hoteldemo.di.module

import com.lastminute.hoteldemo.repository.HotelListRepository
import com.lastminute.hoteldemo.repository.HotelListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideHotelListRepository(listPokemonRepositoryImpl: HotelListRepositoryImpl): HotelListRepository
}