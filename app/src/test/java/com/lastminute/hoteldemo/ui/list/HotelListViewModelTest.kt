package com.lastminute.hoteldemo.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lastminute.hoteldemo.BuildConfig
import com.lastminute.hoteldemo.MainCoroutineRule
import com.lastminute.hoteldemo.UnitTestUtils
import com.lastminute.hoteldemo.api.ApiService
import com.lastminute.hoteldemo.base.CoroutinesDispatcherProvider
import com.lastminute.hoteldemo.domain.HotelListUseCase
import com.lastminute.hoteldemo.getOrAwaitValue
import com.lastminute.hoteldemo.repository.FakeHotelListRepositoryImpl
import com.lastminute.hoteldemo.repository.HotelListRepository
import com.lastminute.hoteldemo.roomdatabase.dao.HotelFilterDAO
import com.lastminute.hoteldemo.ui.list.mvi.HotelListIntent
import com.lastminute.hoteldemo.ui.list.mvi.HotelListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class HotelListViewModelTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dispatcherProvider: CoroutinesDispatcherProvider
    private lateinit var hotelListRepository: HotelListRepository
    private lateinit var apiService: ApiService
    private lateinit var hotelFilterDAO: HotelFilterDAO
    private lateinit var hotelListViewModel: HotelListViewModel

    @Before
    fun setUp() {
        dispatcherProvider = CoroutinesDispatcherProvider(
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher
        )
        hotelFilterDAO = Mockito.mock(HotelFilterDAO::class.java)

        apiService = Retrofit.Builder()
            .baseUrl(MockWebServer().url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `fetchHotelListData - 10`() = runBlocking {
        hotelListRepository = FakeHotelListRepositoryImpl(
            UnitTestUtils.generateHotelList(10), UnitTestUtils.generateHotelFilterObject(),
            dispatcherProvider
        )
        hotelListViewModel =
            HotelListViewModel(HotelListUseCase(hotelListRepository), dispatcherProvider)
        hotelListViewModel.processIntent(HotelListIntent.FetchHotelListData)
        val state = hotelListViewModel.getState().getOrAwaitValue()
        assert(state is HotelListState.OnHotelListSuccess)
        Assert.assertEquals((state as HotelListState.OnHotelListSuccess).dataList.size, 10)
    }

    @Test
    fun `fetchHotelListData - 100`() = runBlocking {
        hotelListRepository = FakeHotelListRepositoryImpl(
            UnitTestUtils.generateHotelList(100), UnitTestUtils.generateHotelFilterObject(),
            dispatcherProvider
        )
        hotelListViewModel =
            HotelListViewModel(HotelListUseCase(hotelListRepository), dispatcherProvider)
        hotelListViewModel.processIntent(HotelListIntent.FetchHotelListData)
        val state = hotelListViewModel.getState().getOrAwaitValue()
        assert(state is HotelListState.OnHotelListSuccess)
        Assert.assertEquals((state as HotelListState.OnHotelListSuccess).dataList.size, 100)
    }
}