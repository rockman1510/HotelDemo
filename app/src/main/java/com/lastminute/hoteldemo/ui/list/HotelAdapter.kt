package com.lastminute.hoteldemo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.api.model.Hotel
import com.lastminute.hoteldemo.databinding.LayoutHotelItemBinding

class HotelAdapter(
    private val hotelSelectedListener: HotelSelectedListener
) : ListAdapter<Hotel, HotelAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutHotelItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_hotel_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), hotelSelectedListener)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class ViewHolder(private val binding: LayoutHotelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hotel: Hotel, selectedListener: HotelSelectedListener) {
            binding.hotel = hotel
            binding.selectedListener = selectedListener
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Hotel>() {
        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
            return oldItem == newItem
        }
    }

}