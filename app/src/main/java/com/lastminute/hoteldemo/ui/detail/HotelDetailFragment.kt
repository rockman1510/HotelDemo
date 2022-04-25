package com.lastminute.hoteldemo.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.databinding.FragmentDetailHotelBinding
import com.lastminute.hoteldemo.utils.BitmapUtils


class HotelDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailHotelBinding
    private val safeArgs: HotelDetailFragmentArgs by navArgs()
    private lateinit var galleryAdapter: HotelGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail_hotel, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        galleryAdapter = HotelGalleryAdapter()
        val hotel = safeArgs.hotel
        binding.hotel = hotel
        binding.vpImage.adapter = galleryAdapter
        galleryAdapter.submitList(safeArgs.hotel.gallery?.toMutableList())
        TabLayoutMediator(binding.tabLayout, binding.vpImage) { _, _ -> }.attach()

        displayGoogleMap(hotel)
        binding.ivBack.setOnClickListener { requireActivity().onBackPressed() }
        binding.btReserve.setOnClickListener {
            Toast.makeText(
                requireContext(), getString(R.string.under_development_msg), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun displayGoogleMap(hotel: Hotel) {
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        supportMapFragment.getMapAsync {
            val markerOptions = MarkerOptions()
            hotel.location?.let { location ->
                val latLng = LatLng(location.latitude, location.longitude)
                markerOptions.position(latLng)
                markerOptions.title(hotel.name)
                it.clear()
                it.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                it.addMarker(markerOptions)?.setIcon(
                    BitmapUtils.bitmapFromVector(
                        requireContext(), R.drawable.ic_apartment
                    )
                )
            }
        }
    }
}