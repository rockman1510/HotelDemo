package com.lastminute.hoteldemo.ui.list.data

import android.os.Parcel
import android.os.Parcelable
import com.lastminute.hoteldemo.constant.Constants

data class HotelFilterObject(
    val id: Long = 0,
    val starFrom: Int = Constants.MIN_STAR,
    val starTo: Int = Constants.MAX_STAR,
    val ratingFrom: Float = Constants.MIN_RATING,
    val ratingTo: Float = Constants.MAX_RATING,
    val priceFrom: Int = Constants.MIN_PRICE,
    val priceTo: Int = Constants.MAX_PRICE,
    val sortType: String? = Constants.DEFAULT_SORT_TYPE
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeInt(starFrom)
        parcel.writeInt(starTo)
        parcel.writeFloat(ratingFrom)
        parcel.writeFloat(ratingTo)
        parcel.writeInt(priceFrom)
        parcel.writeInt(priceTo)
        parcel.writeString(sortType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HotelFilterObject> {
        override fun createFromParcel(parcel: Parcel): HotelFilterObject {
            return HotelFilterObject(parcel)
        }

        override fun newArray(size: Int): Array<HotelFilterObject?> {
            return arrayOfNulls(size)
        }
    }
}