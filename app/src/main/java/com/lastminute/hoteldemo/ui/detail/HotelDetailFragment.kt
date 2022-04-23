package com.lastminute.hoteldemo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.databinding.FragmentDetailHotelBinding

class HotelDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailHotelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list_hotel, container, false)
        return binding.root
    }
}