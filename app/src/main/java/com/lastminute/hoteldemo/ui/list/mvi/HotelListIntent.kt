package com.lastminute.hoteldemo.ui.list.mvi

import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject

sealed class HotelListIntent {
    object FetchHotelListData : HotelListIntent()
    class UpdateHotelFilterData(val hotelFilterObject: HotelFilterObject) : HotelListIntent()
    object SaveSuccessState : HotelListIntent()
}
