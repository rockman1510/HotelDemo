package com.lastminute.hoteldemo.api.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hotel(
    val id: Int,
    val name: String?,
    val location: Location?,
    val stars: Int,
    val checkIn: CheckInOut?,
    val checkOut: CheckInOut?,
    val contact: Contact?,
    val gallery: ArrayList<String>?,
    val userRating: Float,
    val price: Int,
    val currency: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Location::class.java.classLoader),
        parcel.readInt(),
        parcel.readParcelable(CheckInOut::class.java.classLoader),
        parcel.readParcelable(CheckInOut::class.java.classLoader),
        parcel.readParcelable(Contact::class.java.classLoader),
        parcel.readArrayList(String::class.java.classLoader) as ArrayList<String>,
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeParcelable(location, flags)
        parcel.writeInt(stars)
        parcel.writeParcelable(checkIn, flags)
        parcel.writeParcelable(checkOut, flags)
        parcel.writeParcelable(contact, flags)
        parcel.writeList(gallery)
        parcel.writeFloat(userRating)
        parcel.writeInt(price)
        parcel.writeString(currency)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Hotel> {
        override fun createFromParcel(parcel: Parcel): Hotel {
            return Hotel(parcel)
        }

        override fun newArray(size: Int): Array<Hotel?> {
            return arrayOfNulls(size)
        }
    }
}
