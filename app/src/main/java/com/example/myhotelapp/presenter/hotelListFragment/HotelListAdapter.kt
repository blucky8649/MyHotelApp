package com.example.myhotelapp.presenter.hotelListFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhotelapp.databinding.ItemProductBinding
import com.example.myhotelapp.model.Product
import com.example.myhotelapp.presenter.HotelListingViewModel
import com.example.myhotelapp.utils.toFormattedDateString
import com.example.myhotelapp.utils.toWon

class HotelListAdapter(
    private val viewModel: HotelListingViewModel
    ): PagingDataAdapter<Product, HotelListAdapter.HotelListViewHolder>(differCallback) {

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class HotelListViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.apply {
                tvCurrentDateTime.text = item.time?.toFormattedDateString() ?: ""
                tvHotelName.text = item.name
                tvHotelPrice.text = "${item.description.price.toWon()}원"
                ratingbarSmall.rating = item.rate.div(2).toFloat()
                tvRatingScore.text = item.rate.toString()
                Glide.with(root)
                    .load(item.thumbnail)
                    .centerCrop()
                    .into(ivProductImage)

                btnLike.apply {
                    isChecked = item.likeState
                    setOnClickListener {
                        when (item.likeState) {
                            true -> {
                                viewModel.setLikeState(
                                    item = item,
                                    state = false
                                )
                            }
                            false -> {
                                viewModel.setLikeState(
                                    item = item,
                                    state = true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelListViewHolder {
        return HotelListViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HotelListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}