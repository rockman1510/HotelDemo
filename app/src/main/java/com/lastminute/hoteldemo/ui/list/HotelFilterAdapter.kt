package com.lastminute.hoteldemo.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.databinding.LayoutFilterHotelItemBinding

class HotelFilterAdapter : ListAdapter<String, HotelFilterAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutFilterHotelItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_filter_hotel_item,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun addItem(newItem: String){
        currentList.add(newItem)
        submitList(currentList.toMutableList())
    }

    class ViewHolder(private val binding: LayoutFilterHotelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filter: String) {
            binding.filter = filter
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}