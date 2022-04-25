package com.lastminute.hoteldemo.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lastminute.hoteldemo.constant.Constants

@Entity(tableName = "HotelFilterEntity")
class HotelFilterEntity() {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var starFrom: Int = Constants.MIN_STAR
    var starTo: Int = Constants.MAX_STAR
    var ratingFrom: Float = Constants.MIN_RATING
    var ratingTo: Float = Constants.MAX_RATING
    var priceFrom: Int = Constants.MIN_PRICE
    var priceTo: Int = Constants.MAX_PRICE
    var sortType: String? = Constants.DEFAULT_SORT_TYPE

    constructor(
        id: Long = 0,
        starFrom: Int = Constants.MIN_STAR,
        starTo: Int = Constants.MAX_STAR,
        ratingFrom: Float = Constants.MIN_RATING,
        ratingTo: Float = Constants.MAX_RATING,
        priceFrom: Int = Constants.MIN_PRICE,
        priceTo: Int = Constants.MAX_PRICE,
        sortType: String? = Constants.DEFAULT_SORT_TYPE
    ) : this() {
        this.id = id
        this.starFrom = starFrom
        this.starTo = starTo
        this.ratingFrom = ratingFrom
        this.ratingTo = ratingTo
        this.priceFrom = priceFrom
        this.priceTo = priceTo
        this.sortType = sortType
    }
}