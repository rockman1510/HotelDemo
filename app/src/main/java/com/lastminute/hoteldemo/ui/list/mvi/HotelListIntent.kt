package com.lastminute.hoteldemo.ui.list.mvi

sealed class HotelListIntent {
    class FetchData() : HotelListIntent()
    object SaveSuccessState : HotelListIntent()
}
