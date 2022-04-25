package com.lastminute.hoteldemo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lastminute.hoteldemo.BuildConfig
import com.lastminute.hoteldemo.MainCoroutineRule
import com.lastminute.hoteldemo.api.ApiService
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RemoteCallRepositoryImplTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var hotelListRepository: FakeRepositoryForRemoteImpl
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        val dispatcherProvider = CoroutinesDispatcherProvider(
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher
        )
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
        hotelListRepository =
            FakeRepositoryForRemoteImpl(apiService, dispatcherProvider)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getHotelList - 10`() = runBlocking {
        var dataList = arrayListOf<Hotel>()
        hotelListRepository.getHotelList().collect {
            dataList = it
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 10)
    }
}