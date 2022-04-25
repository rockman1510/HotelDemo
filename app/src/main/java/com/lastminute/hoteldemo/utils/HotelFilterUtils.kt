package com.lastminute.hoteldemo.utils

import com.lastminute.hoteldemo.constant.SortType
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject
import com.lastminute.hoteldemo.roomdatabase.entity.HotelFilterEntity

object HotelFilterUtils {

    fun fromEntityToObject(hotelFilterEntity: HotelFilterEntity?): HotelFilterObject {
        return if (hotelFilterEntity != null) {
            HotelFilterObject(
                hotelFilterEntity.id, hotelFilterEntity.starFrom, hotelFilterEntity.starTo,
                hotelFilterEntity.ratingFrom, hotelFilterEntity.ratingTo,
                hotelFilterEntity.priceFrom, hotelFilterEntity.priceTo,
                hotelFilterEntity.sortType
            )
        } else {
            HotelFilterObject()
        }
    }

    fun fromObjectToEntity(hotelFilterObject: HotelFilterObject): HotelFilterEntity {
        return HotelFilterEntity(
            hotelFilterObject.id, hotelFilterObject.starFrom, hotelFilterObject.starTo,
            hotelFilterObject.ratingFrom, hotelFilterObject.ratingTo,
            hotelFilterObject.priceFrom, hotelFilterObject.priceTo, hotelFilterObject.sortType
        )
    }

    fun updateNewObject(
        hotelFilterObject: HotelFilterObject?, sortType: SortType
    ): HotelFilterObject {
        return if (hotelFilterObject != null) {
            HotelFilterObject(
                hotelFilterObject.id, hotelFilterObject.starFrom, hotelFilterObject.starTo,
                hotelFilterObject.ratingFrom, hotelFilterObject.ratingTo,
                hotelFilterObject.priceFrom, hotelFilterObject.priceTo, sortType.name
            )
        } else {
            return HotelFilterObject(sortType = sortType.name)
        }
    }
}