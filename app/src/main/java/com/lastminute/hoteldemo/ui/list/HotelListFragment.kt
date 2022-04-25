package com.lastminute.hoteldemo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.constant.Constants
import com.lastminute.hoteldemo.constant.SortType
import com.lastminute.hoteldemo.databinding.FragmentListHotelBinding
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject
import com.lastminute.hoteldemo.ui.list.mvi.HotelListIntent
import com.lastminute.hoteldemo.ui.list.mvi.HotelListState
import com.lastminute.hoteldemo.utils.HotelFilterUtils
import com.lastminute.hoteldemo.utils.SortTypeUtils
import com.lastminute.hoteldemo.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelListFragment : Fragment(), HotelSelectedListener {

    private lateinit var binding: FragmentListHotelBinding
    private lateinit var viewModel: HotelListViewModel
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var hotelFilterAdapter: HotelFilterAdapter
    private var hotelFilterObject: HotelFilterObject? = null
    private var currency: String = Constants.DEFAULT_CURRENCY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_hotel, container, false)
        viewModel = ViewModelProvider(this)[HotelListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.processIntent(HotelListIntent.FetchHotelListData)
        lifecycleScope.launchWhenResumed {
            viewModel.getState().observe(viewLifecycleOwner, ::onCallbackState)
        }
    }

    private fun initViews() {
        binding.rvFilter.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHotel.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        hotelAdapter = HotelAdapter(this)
        hotelFilterAdapter = HotelFilterAdapter()
        binding.hotelAdapter = hotelAdapter
        binding.hotelFilterAdapter = hotelFilterAdapter
        binding.swipeLayout.setOnRefreshListener {
            viewModel.processIntent(HotelListIntent.FetchHotelListData)
        }
        binding.ivFilter.setOnClickListener {
            hotelFilterObject?.apply {
                HotelFilterDialogFragment.newInstance(::onCallbackHotelFilter, this, currency)
                    .show(childFragmentManager, HotelFilterDialogFragment.TAG)
            }
        }
        binding.ivSort.setOnClickListener {
            hotelFilterObject?.apply {
                HotelSortDialogFragment.newInstance(
                    ::onCallbackHotelSortType,
                    this.sortType.toString()
                ).show(childFragmentManager, HotelSortDialogFragment.TAG)
            }
        }
    }

    private fun onCallbackState(state: HotelListState?) {
        when (state) {
            is HotelListState.OnLoading -> {
                binding.swipeLayout.isRefreshing = true
            }
            is HotelListState.OnHotelListSuccess -> {
                binding.swipeLayout.isRefreshing = false
                doingHotelListSuccess(state)
            }
            is HotelListState.OnHotelFilterUpdatedSuccess -> {
                if (state.isSuccess) {
                    viewModel.processIntent(HotelListIntent.FetchHotelListData)
                } else {
                    binding.swipeLayout.isRefreshing = false
                    Toast.makeText(
                        requireContext(), getString(R.string.error_occur_msg), Toast.LENGTH_SHORT
                    ).show()
                }
            }
            is HotelListState.OnError -> {
                binding.swipeLayout.isRefreshing = false
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun doingHotelListSuccess(state: HotelListState.OnHotelListSuccess) {
        hotelAdapter.submitList(state.dataList.toMutableList())
        if (state.dataList.size > 0) {
            currency = state.dataList[0].currency.toString()
        } else {
            Toast.makeText(
                requireContext(), getString(R.string.no_data_msg), Toast.LENGTH_SHORT
            ).show()
        }
        hotelFilterObject = state.hotelFilterObject
        val filterList = StringUtils.convertFilterHotelString(
            requireContext(), hotelFilterObject, currency
        )
        if (hotelFilterObject?.sortType != Constants.DEFAULT_SORT_TYPE) {
            filterList.add(
                SortTypeUtils.getSortString(
                    requireContext(), SortType.valueOf(hotelFilterObject?.sortType.toString())
                )
            )
        }
        hotelFilterAdapter.submitList(filterList.toMutableList())
    }

    private fun onCallbackHotelFilter(hotelFilterObject: HotelFilterObject?) {
        hotelFilterObject?.let {
            viewModel.processIntent(HotelListIntent.UpdateHotelFilterData(it))
        }
    }

    private fun onCallbackHotelSortType(sortType: SortType?) {
        sortType?.let {
            hotelFilterObject = HotelFilterUtils.updateNewObject(hotelFilterObject, it)
            hotelFilterObject?.let { filterObject ->
                viewModel.processIntent(HotelListIntent.UpdateHotelFilterData(filterObject))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        saveStateAction()
    }

    private fun saveStateAction() {
        hotelAdapter.let {
            viewModel.processIntent(HotelListIntent.SaveSuccessState)
        }
    }

    override fun onSelectedHotel(hotel: Hotel) {
        findNavController().navigate(HotelListFragmentDirections.goToDetail(hotel))
    }
}