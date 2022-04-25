package com.lastminute.hoteldemo.utils

import android.annotation.SuppressLint
import android.content.Context
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.api.model.Location
import com.lastminute.hoteldemo.constant.Constants
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject

object StringUtils {
    @JvmStatic
    fun ratingToText(rating: Float = 0.0f, context: Context): String {
        var result = ""
        when {
            rating in 0.0..3.0 -> {
                result = context.getString(R.string.very_bad)
            }
            rating in 3.1..6.0 -> {
                result = context.getString(R.string.bad)
            }
            rating in 6.1..7.5 -> {
                result = context.getString(R.string.good)
            }
            rating in 7.6..8.9 -> {
                result = context.getString(R.string.very_good)
            }
            rating >= 9.0 -> {
                result = context.getString(R.string.excellent)
            }
        }
        return result
    }

    @JvmStatic
    fun convertAddress(location: Location?): String {
        return "${location?.address.toString()}, ${location?.city.toString()}"
    }

    @JvmStatic
    fun convertPrice(price: Int, currency: String): String {
        return "$currency $price"
    }

    @JvmStatic
    fun generateGoogleMapString(
        lat: Double, long: Double, zoom: Int, size: Int, context: Context
    ): String {
        return "http://maps.google.com/maps/api/staticmap?center=$lat,$long" +
                "&zoom=$zoom&size=${size}x${size}&sensor=false&key=" +
                context.getString(R.string.google_api_key)
    }

    @SuppressLint("StringFormatMatches")
    @JvmStatic
    fun convertFilterHotelString(
        context: Context,
        hotelFilter: HotelFilterObject?,
        currency: String
    ): ArrayList<String> {
        val arrayList = ArrayList<String>()
        hotelFilter?.let {
            if (it.starFrom != Constants.MIN_STAR || it.starTo != Constants.MAX_STAR) {
                arrayList.add(
                    context.getString(
                        R.string.generic_stars,
                        hotelFilter.starFrom.toString(),
                        hotelFilter.starTo.toString()
                    )
                )
            }
            if (it.ratingFrom != Constants.MIN_RATING || it.ratingTo != Constants.MAX_RATING) {
                arrayList.add(
                    context.getString(
                        R.string.generic_ratings,
                        hotelFilter.ratingFrom.toString(),
                        hotelFilter.ratingTo.toString()
                    )
                )
            }
            if (it.priceFrom != Constants.MIN_PRICE || it.priceTo != Constants.MAX_PRICE) {
                arrayList.add(
                    context.getString(
                        R.string.generic_price,
                        currency,
                        hotelFilter.priceFrom.toString(),
                        "${hotelFilter.priceTo}+"
                    )
                )
            }
        }
        return arrayList
    }
}