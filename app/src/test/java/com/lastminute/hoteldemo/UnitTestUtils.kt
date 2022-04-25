package com.lastminute.hoteldemo

import com.lastminute.hoteldemo.api.model.CheckInOut
import com.lastminute.hoteldemo.api.model.Contact
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.api.model.Location
import com.lastminute.hoteldemo.constant.Constants
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject


object UnitTestUtils {
    fun generateHotelFilterObject(
        id: Long = 0,
        starFrom: Int = Constants.MIN_STAR,
        starTo: Int = Constants.MAX_STAR,
        ratingFrom: Float = Constants.MIN_RATING,
        ratingTo: Float = Constants.MAX_RATING,
        priceFrom: Int = Constants.MIN_PRICE,
        priceTo: Int = Constants.MAX_PRICE,
        sortType: String? = Constants.DEFAULT_SORT_TYPE
    ): HotelFilterObject {
        return HotelFilterObject(
            id, starFrom, starTo, ratingFrom, ratingTo, priceFrom, priceTo, sortType
        )
    }

    fun generateHotelList(limit: Int = 10): ArrayList<Hotel> {
        val data = arrayListOf<Hotel>()
        for (i in 0 until limit) {
            data.add(
                Hotel(
                    i, "name $i",
                    Location("address $i", "city $i", 0.0, 0.0),
                    i % 2,
                    CheckInOut("${i + 1}:00", "${i + 1}:00"),
                    CheckInOut("${i + 3}:00", "${i + 3}:00"),
                    Contact("123456$i", "123456$i@email.com"),
                    arrayListOf(), 5.5f, 100 + i, "EUR"
                )
            )
        }
        return data
    }
}