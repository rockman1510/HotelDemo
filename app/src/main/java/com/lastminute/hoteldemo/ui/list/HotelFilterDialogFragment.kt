package com.lastminute.hoteldemo.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.constant.Constants
import com.lastminute.hoteldemo.databinding.FragmentFilterDialogBinding
import com.lastminute.hoteldemo.ui.extention.build
import com.lastminute.hoteldemo.ui.list.data.HotelFilterObject

class HotelFilterDialogFragment(private val changeCallback: (hotelFilterObject: HotelFilterObject?) -> Unit) :
    BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private lateinit var binding: FragmentFilterDialogBinding
    private var hotelFilterObject: HotelFilterObject? = null
    private var currency = Constants.DEFAULT_CURRENCY
    private var sortType = Constants.DEFAULT_SORT_TYPE
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_filter_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        hotelFilterObject = arguments?.getParcelable(KEY_HOTEL_FILTER)
        currency = arguments?.getString(KEY_CURRENCY).toString()
        sortType = hotelFilterObject?.sortType.toString()
        initRangeSliderViews()
        setTextValues(hotelFilterObject)
        setRangeSliderValues(hotelFilterObject)
        initOnClicks()
        initRangeSliderListeners()
    }

    private fun initRangeSliderViews() {
        binding.rsStar.isTickVisible = false
        binding.rsRating.isTickVisible = false
        binding.rsPrice.isTickVisible = false
        binding.rsStar.setMinSeparationValue(1.0f)
        binding.rsRating.setMinSeparationValue(1.0f)
        binding.rsPrice.setMinSeparationValue(10.0f)
    }

    @SuppressLint("StringFormatMatches")
    private fun setTextValues(`object`: HotelFilterObject?) {
        `object`?.let {
            binding.tvStar.text = getString(R.string.generic_stars, it.starFrom, it.starTo)
            binding.tvRating.text = getString(R.string.generic_ratings, it.ratingFrom, it.ratingTo)
            var priceTo = it.priceTo.toString()
            if (it.priceTo >= Constants.MAX_PRICE) {
                priceTo = "${priceTo}+"
            }
            binding.tvPrice.text =
                getString(R.string.generic_price, currency, it.priceFrom, priceTo)
        }
    }

    private fun setRangeSliderValues(hotelFilterObject: HotelFilterObject?) {
        hotelFilterObject?.let {
            binding.rsStar.setValues(it.starFrom.toFloat(), it.starTo.toFloat())
            binding.rsRating.setValues(it.ratingFrom, it.ratingTo)
            binding.rsPrice.setValues(it.priceFrom.toFloat(), it.priceTo.toFloat())
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun initRangeSliderListeners() {
        binding.rsStar.addOnChangeListener { slider, value, fromUser ->
            binding.tvStar.text =
                getString(
                    R.string.generic_stars, slider.values[0]?.toInt(), slider.values[1]?.toInt()
                )
        }
        binding.rsRating.addOnChangeListener { slider, value, fromUser ->
            binding.tvRating.text =
                getString(
                    R.string.generic_ratings,
                    slider.values[0]?.toString(),
                    slider.values[1]?.toString()
                )
        }
        binding.rsPrice.addOnChangeListener { slider, value, fromUser ->
            var pricesTo = slider.values[1]?.toInt().toString()
            if (slider.values[1]?.toInt() == Constants.MAX_PRICE)
                pricesTo = "${pricesTo}+"
            binding.tvPrice.text =
                getString(
                    R.string.generic_price, currency, slider.values[0]?.toInt(), pricesTo
                )
        }
    }

    private fun initOnClicks() {
        binding.btClearAll.setOnClickListener {
            setTextValues(HotelFilterObject())
            setRangeSliderValues(HotelFilterObject())
        }
        binding.btOk.setOnClickListener {
            var id = 0L
            hotelFilterObject?.id?.let { id = it }
            hotelFilterObject = HotelFilterObject(
                id, binding.rsStar.values[0].toInt(), binding.rsStar.values[1].toInt(),
                binding.rsRating.values[0], binding.rsRating.values[1],
                binding.rsPrice.values[0].toInt(), binding.rsPrice.values[1].toInt(),
                sortType
            )
            changeCallback.invoke(hotelFilterObject)
            dismiss()
        }
        binding.ivClose.setOnClickListener { dismiss() }
    }

    companion object {
        val TAG: String = HotelFilterDialogFragment::class.java.simpleName
        private const val KEY_HOTEL_FILTER = "KEY_HOTEL_FILTER"
        private const val KEY_CURRENCY = "KEY_CURRENCY"

        fun newInstance(
            callback: (callbackObject: HotelFilterObject?) -> Unit,
            hotelFilterObject: HotelFilterObject, currency: String
        ): HotelFilterDialogFragment = HotelFilterDialogFragment(callback).build {
            putParcelable(KEY_HOTEL_FILTER, hotelFilterObject)
            putString(KEY_CURRENCY, currency)
        }
    }

}