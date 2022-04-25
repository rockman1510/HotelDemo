package com.lastminute.hoteldemo.ui.list

import com.lastminute.hoteldemo.api.model.Hotel

interface HotelSelectedListener {
    fun onSelectedHotel(hotel: Hotel)
}