package com.lastminute.hoteldemo.ui.list.mvi

import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject

sealed class HotelListState {
    object OnLoading : HotelListState()
    class OnHotelListSuccess(val dataList: ArrayList<Hotel>, val hotelFilterObject: HotelFilterObject) : HotelListState()
    class OnHotelFilterUpdatedSuccess(val isSuccess: Boolean): HotelListState()
    class OnError(val message: String) : HotelListState()
}