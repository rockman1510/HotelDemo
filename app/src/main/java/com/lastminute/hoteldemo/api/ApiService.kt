package com.lastminute.hoteldemo.api

import com.lastminute.hoteldemo.api.model.Hotel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("eef3c24d-5bfd-4881-9af7-0b404ce09507")
    suspend fun getHotelListApi(): Response<List<Hotel>>
}