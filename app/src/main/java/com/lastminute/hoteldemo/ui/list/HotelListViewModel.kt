package com.lastminute.hoteldemo.ui.list

import com.lastminute.hoteldemo.base.mvi.BaseViewModel
import com.lastminute.hoteldemo.domain.HotelListUseCase
import com.lastminute.hoteldemo.ui.list.mvi.HotelListIntent
import com.lastminute.hoteldemo.ui.list.mvi.HotelListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotelListViewModel @Inject constructor(val hotelListUseCase: HotelListUseCase) :
    BaseViewModel<HotelListIntent, HotelListState>() {
    override fun processIntent(intent: HotelListIntent) {
        TODO("Not yet implemented")
    }
}