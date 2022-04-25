package com.lastminute.hoteldemo.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.base.CoroutinesDispatcherProvider
import com.lastminute.hoteldemo.base.mvi.BaseViewModel
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject
import com.lastminute.hoteldemo.domain.HotelListUseCase
import com.lastminute.hoteldemo.ui.list.mvi.HotelListIntent
import com.lastminute.hoteldemo.ui.list.mvi.HotelListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelListViewModel @Inject constructor(
    private val hotelListUseCase: HotelListUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel<HotelListIntent, HotelListState>() {

    override val state: MutableLiveData<HotelListState> = MutableLiveData()
    private var hotelList = arrayListOf<Hotel>()
    private var hotelFilterObject = HotelFilterObject()

    override fun processIntent(intent: HotelListIntent) {
        state.postValue(HotelListState.OnLoading)
        when (intent) {
            is HotelListIntent.FetchHotelListData -> {
                viewModelScope.launch(dispatcherProvider.Main) {
                    hotelListUseCase.fetchHotelList(::onHotelListCallback, ::onErrorMessage)
                }
            }
            is HotelListIntent.UpdateHotelFilterData -> {
                viewModelScope.launch(dispatcherProvider.Main) {
                    hotelListUseCase.updateHotelFilter(
                        intent.hotelFilterObject, ::onUpdateSuccess, ::onErrorMessage
                    )
                }
            }

            is HotelListIntent.SaveSuccessState -> {
                state.postValue(HotelListState.OnHotelListSuccess(hotelList, hotelFilterObject))
            }
        }
    }


    private fun onHotelListCallback(
        dataList: ArrayList<Hotel>, hotelFilterObject: HotelFilterObject
    ) {
        hotelList = dataList
        this.hotelFilterObject = hotelFilterObject
        state.postValue(HotelListState.OnHotelListSuccess(hotelList, this.hotelFilterObject))
    }

    private fun onErrorMessage(errorMessage: String) {
        state.postValue(HotelListState.OnError(errorMessage))
    }

    private fun onUpdateSuccess(isSuccess: Boolean) {
        state.postValue(HotelListState.OnHotelFilterUpdatedSuccess(isSuccess))
    }
}