package com.lastminute.hoteldemo.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lastminute.hoteldemo.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get 10 Hotel List`() = runBlocking {
        val hotelList = service.getHotelListApi()
        Assert.assertEquals(hotelList.body()?.size, 10)
    }
}