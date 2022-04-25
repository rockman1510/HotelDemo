package com.lastminute.hoteldemo.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject
import com.lastminute.hoteldemo.utils.HotelFilterUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HotelListRepositoryImplTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var hotelListRepository: HotelListRepository


    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun getHotelList() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        hotelListRepository.getHotelList().collect {
            dataList = it
        }
        assertNotNull(dataList)
        assertEquals(dataList.size, 10)
    }


    @Test
    fun insertHotelFilter() = runBlocking {
        hotelListRepository.updateHotelFilterLocalData(
            HotelFilterUtils.fromObjectToEntity(
                HotelFilterObject(starFrom = 1, starTo = 4)
            )
        )
        var dataList = arrayListOf<Hotel>()
        hotelListRepository.getHotelFilterLocalData().collect { hotelFilterEntity ->
            val hotelFilterObject = HotelFilterUtils.fromEntityToObject(hotelFilterEntity)
            hotelListRepository.getHotelList().map {
                it.filter { hotel ->
                    hotel.stars >= hotelFilterObject.starFrom && hotel.stars <= hotelFilterObject.starTo
                }
            }.collect { output ->
                dataList = ArrayList(output)
            }
        }
        assertNotNull(dataList)
        assertEquals(dataList.size, 7)
    }
}