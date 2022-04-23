package com.lastminute.hoteldemo.api.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckInOut(
    val from: String?,
    val to: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(from)
        parcel.writeString(to)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CheckInOut> {
        override fun createFromParcel(parcel: Parcel): CheckInOut {
            return CheckInOut(parcel)
        }

        override fun newArray(size: Int): Array<CheckInOut?> {
            return arrayOfNulls(size)
        }
    }
}