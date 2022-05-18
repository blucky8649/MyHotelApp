package com.example.myhotelapp.presenter.hotelDetailFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myhotelapp.databinding.ItemTagItemsBinding
import com.example.myhotelapp.model.Tags

class TagListAdapter: ListAdapter<Tags, TagListViewHolder>(differCallback) {

    companion object {
        private val differCallback = object : DiffUtil.ItemCallback<Tags>() {
            override fun areItemsTheSame(oldItem: Tags, newItem: Tags): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tags, newItem: Tags): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagListViewHolder {
        return TagListViewHolder(
            ItemTagItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TagListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
class TagListViewHolder(val binding: ItemTagItemsBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Tags) {
        binding.tvTagItemName.text = item.name
    }
}