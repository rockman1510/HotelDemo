package com.lastminute.hoteldemo.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.constant.SortType
import com.lastminute.hoteldemo.databinding.FragmentSortDialogBinding
import com.lastminute.hoteldemo.ui.extention.build
import com.lastminute.hoteldemo.utils.SortTypeUtils

class HotelSortDialogFragment(private val changeCallbackSortType: (sortType: SortType?) -> Unit) :
    BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private lateinit var binding: FragmentSortDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sort_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val sortType = SortType.valueOf(arguments?.getString(KEY_HOTEL_SORT).toString())
        binding.radioGroup.check(SortTypeUtils.getRadioButtonId(sortType, binding))
        binding.radioGroup.setOnCheckedChangeListener { _, i ->
            changeCallbackSortType.invoke(SortTypeUtils.getSortType(i, binding))
            dismiss()
        }
    }

    companion object {
        val TAG: String = HotelSortDialogFragment::class.java.simpleName
        private const val KEY_HOTEL_SORT = "KEY_HOTEL_SORT"

        fun newInstance(
            callback: (sortType: SortType?) -> Unit,
            sortTypeName: String
        ): HotelSortDialogFragment = HotelSortDialogFragment(callback).build {
            putString(KEY_HOTEL_SORT, sortTypeName)
        }
    }
}