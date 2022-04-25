package com.lastminute.hoteldemo.utils

import android.content.Context
import com.lastminute.hoteldemo.R
import com.lastminute.hoteldemo.constant.SortType
import com.lastminute.hoteldemo.databinding.FragmentSortDialogBinding

object SortTypeUtils {
    fun getSortType(viewId: Int, binding: FragmentSortDialogBinding): SortType {
        when (viewId) {
            binding.rbDefault.id -> {
                return SortType.DEFAULT
            }
            binding.rbStarDown.id -> {
                return SortType.STAR_DOWN
            }
            binding.rbStarUp.id -> {
                return SortType.STAR_UP
            }
            binding.rbRatingDown.id -> {
                return SortType.RATING_DOWN
            }
            binding.rbRatingUp.id -> {
                return SortType.RATING_UP
            }
            binding.rbPriceDown.id -> {
                return SortType.PRICE_DOWN
            }
            binding.rbPriceUp.id -> {
                return SortType.PRICE_UP
            }
        }
        return SortType.DEFAULT
    }

    fun getRadioButtonId(sortType: SortType, binding: FragmentSortDialogBinding): Int {
        return when (sortType) {
            SortType.DEFAULT -> {
                binding.rbDefault.id
            }
            SortType.STAR_DOWN -> {
                binding.rbStarDown.id
            }
            SortType.STAR_UP -> {
                binding.rbStarUp.id
            }
            SortType.RATING_DOWN -> {
                binding.rbRatingDown.id
            }
            SortType.RATING_UP -> {
                binding.rbRatingUp.id
            }
            SortType.PRICE_DOWN -> {
                binding.rbPriceDown.id
            }
            SortType.PRICE_UP -> {
                binding.rbPriceUp.id
            }
        }
    }

    fun getSortString(context: Context, sortType: SortType): String {
        val result = when (sortType) {
            SortType.DEFAULT -> {
                context.getString(R.string.sort_default)
            }
            SortType.STAR_DOWN -> {
                context.getString(R.string.star_down)
            }
            SortType.STAR_UP -> {
                context.getString(R.string.star_up)
            }
            SortType.RATING_DOWN -> {
                context.getString(R.string.rating_down)
            }
            SortType.RATING_UP -> {
                context.getString(R.string.rating_up)
            }
            SortType.PRICE_DOWN -> {
                context.getString(R.string.price_down)
            }
            SortType.PRICE_UP -> {
                context.getString(R.string.price_up)
            }
        }
        return context.getString(R.string.generic_sort, result)
    }
}