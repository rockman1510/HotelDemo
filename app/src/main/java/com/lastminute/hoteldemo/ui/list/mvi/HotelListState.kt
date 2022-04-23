package com.lastminute.hoteldemo.ui.list.mvi

import com.lastminute.hoteldemo.api.model.Hotel

sealed class HotelListState {
    object OnLoading : HotelListState()
    class OnSuccess(val dataList: ArrayList<Hotel>) : HotelListState()
    class OnError(val message: String) : HotelListState()
}