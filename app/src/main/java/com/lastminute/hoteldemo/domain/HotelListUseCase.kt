package com.lastminute.hoteldemo.domain

import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.constant.SortType
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject
import com.lastminute.hoteldemo.repository.HotelListRepository
import com.lastminute.hoteldemo.utils.HotelFilterUtils
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HotelListUseCase @Inject constructor(
    private val hotelListRepository: HotelListRepository
) {
    suspend fun fetchHotelList(
        callbackData: (dataList: ArrayList<Hotel>, hotelFilterObject: HotelFilterObject) -> Unit,
        callbackError: (messageError: String) -> Unit
    ) {
        try {
            hotelListRepository.getHotelFilterLocalData().collect { hotelFilterEntity ->
                val hotelFilterObject = HotelFilterUtils.fromEntityToObject(hotelFilterEntity)
                hotelListRepository.getHotelList().map {
                    it.filter { hotel ->
                        hotel.stars >= hotelFilterObject.starFrom && hotel.stars <= hotelFilterObject.starTo
                                && hotel.userRating >= hotelFilterObject.ratingFrom && hotel.userRating <= hotelFilterObject.ratingTo &&
                                hotel.price >= hotelFilterObject.priceFrom && hotel.price <= hotelFilterObject.priceTo
                    }
                }.collect {
                    callbackData.invoke(
                        sortDataList(SortType.valueOf(hotelFilterObject.sortType.toString()), it),
                        hotelFilterObject
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callbackError.invoke(e.message.toString())
        }
    }

    private fun sortDataList(sortType: SortType, dataList: List<Hotel>): ArrayList<Hotel> {
        return when (sortType) {
            SortType.STAR_DOWN -> ArrayList(dataList.sortedByDescending { it.stars })
            SortType.STAR_UP -> ArrayList(dataList.sortedBy { it.stars })
            SortType.RATING_DOWN -> ArrayList(dataList.sortedByDescending { it.userRating })
            SortType.RATING_UP -> ArrayList(dataList.sortedBy { it.userRating })
            SortType.PRICE_DOWN -> ArrayList(dataList.sortedByDescending { it.price })
            SortType.PRICE_UP -> ArrayList(dataList.sortedBy { it.price })
            SortType.DEFAULT -> ArrayList(dataList)
        }
    }

    suspend fun updateHotelFilter(
        hotelFilterObject: HotelFilterObject,
        callbackSuccess: (success: Boolean) -> Unit,
        callbackError: (messageError: String) -> Unit
    ) {
        try {
            hotelListRepository.updateHotelFilterLocalData(
                HotelFilterUtils.fromObjectToEntity(hotelFilterObject)
            )
            callbackSuccess.invoke(true)
        } catch (e: Exception) {
            e.printStackTrace()
            callbackError.invoke(e.message.toString())
        }
    }
}