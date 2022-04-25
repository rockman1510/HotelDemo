package com.lastminute.hoteldemo.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lastminute.hoteldemo.BuildConfig
import com.lastminute.hoteldemo.MainCoroutineRule
import com.lastminute.hoteldemo.UnitTestUtils
import com.lastminute.hoteldemo.api.ApiService
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.base.CoroutinesDispatcherProvider
import com.lastminute.hoteldemo.repository.FakeRepositoryForRemoteImpl
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HotelListUseCaseTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dispatcherProvider: CoroutinesDispatcherProvider
    private lateinit var hotelListRepository: FakeRepositoryForRemoteImpl
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        dispatcherProvider = CoroutinesDispatcherProvider(
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher
        )

        apiService = Retrofit.Builder()
            .baseUrl(MockWebServer().url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
        hotelListRepository =
            FakeRepositoryForRemoteImpl(apiService, dispatcherProvider)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `fetchHotelList - 10`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        val hotelFilterObject = UnitTestUtils.generateHotelFilterObject()

        hotelListRepository.getHotelList().map {
            it.filter { hotel ->
                hotel.stars >= hotelFilterObject.starFrom && hotel.stars <= hotelFilterObject.starTo
                        && hotel.userRating >= hotelFilterObject.ratingFrom && hotel.userRating <= hotelFilterObject.ratingTo &&
                        hotel.price >= hotelFilterObject.priceFrom && hotel.price <= hotelFilterObject.priceTo
            }
        }.collect {
            dataList = ArrayList(it)
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 10)

    }

    @Test
    fun `fetchHotelList - Star 0 - 3`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        val hotelFilterObject = UnitTestUtils.generateHotelFilterObject(starTo = 3)

        hotelListRepository.getHotelList().map {
            it.filter { hotel ->
                hotel.stars <= hotelFilterObject.starTo
            }
        }.collect {
            dataList = ArrayList(it)
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 1)

    }

    @Test
    fun `fetchHotelList - Star 0 - 4`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        val hotelFilterObject = UnitTestUtils.generateHotelFilterObject(starTo = 4)

        hotelListRepository.getHotelList().map {
            it.filter { hotel ->
                hotel.stars <= hotelFilterObject.starTo
            }
        }.collect {
            dataList = ArrayList(it)
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 7)
    }

    @Test
    fun `fetchHotelList - Rating 5 - 8`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        val hotelFilterObject =
            UnitTestUtils.generateHotelFilterObject(ratingFrom = 5.0f, ratingTo = 8.0f)

        hotelListRepository.getHotelList().map {
            it.filter { hotel ->
                hotel.userRating >= hotelFilterObject.ratingFrom && hotel.userRating <= hotelFilterObject.ratingTo
            }
        }.collect {
            dataList = ArrayList(it)
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 4)
    }

    @Test
    fun `fetchHotelList - Rating from 9`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        val hotelFilterObject = UnitTestUtils.generateHotelFilterObject(ratingFrom = 9.0f)

        hotelListRepository.getHotelList().map {
            it.filter { hotel ->
                hotel.userRating >= hotelFilterObject.ratingFrom
            }
        }.collect {
            dataList = ArrayList(it)
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 3)
    }

    @Test
    fun `fetchHotelList - Price upto 100`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        val hotelFilterObject = UnitTestUtils.generateHotelFilterObject(priceTo = 100)

        hotelListRepository.getHotelList().map {
            it.filter { hotel ->
                hotel.price <= hotelFilterObject.priceTo
            }
        }.collect {
            dataList = ArrayList(it)
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 3)
    }

    @Test
    fun `fetchHotelList - Price from 300`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        val hotelFilterObject = UnitTestUtils.generateHotelFilterObject(priceFrom = 300)

        hotelListRepository.getHotelList().map {
            it.filter { hotel ->
                hotel.price >= hotelFilterObject.priceFrom
            }
        }.collect {
            dataList = ArrayList(it)
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 1)
    }

    @Test
    fun `fetchHotelList - Sort Price decreasing`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        hotelListRepository.getHotelList().collect {
            dataList = ArrayList(it.sortedByDescending { hotel -> hotel.price })
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 10)
        Assert.assertEquals(dataList[0].price, 400)
        Assert.assertEquals(dataList[dataList.size - 1].price, 12)
    }
}