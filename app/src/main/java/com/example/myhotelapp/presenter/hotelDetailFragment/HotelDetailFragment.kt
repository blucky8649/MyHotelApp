package com.example.myhotelapp.presenter.hotelDetailFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhotelapp.databinding.FragmentHotelDetailBinding
import com.example.myhotelapp.model.Product
import com.example.myhotelapp.model.Tags
import com.example.myhotelapp.presenter.HotelListingViewModel
import com.example.myhotelapp.utils.toWon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelDetailFragment: Fragment() {

    val args: HotelDetailFragmentArgs by navArgs()
    val tagAdapter: TagListAdapter by lazy {
        TagListAdapter()
    }
    val viewModel: HotelListingViewModel by activityViewModels()
    private var _binding: FragmentHotelDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHotelDetailBinding.inflate(layoutInflater, container, false)

        val product = args.product
        init(product)
        setupRecyclerView()
        tagAdapter.submitList(makeTags(product.description.subject))

        return binding.root
    }
    private fun makeTags(tag: String) = tag.split(", ").mapIndexed { index, s -> Tags(index, "#$s") }
    private fun setupRecyclerView() {
        binding.rvTagList.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun init(item: Product) {
        binding.apply {
            Glide.with(binding.root)
                .load(item.description.imagePath)
                .centerCrop()
                .into(ivThumbnail)
            tvPriceDetail.text = item.description.price.toWon()
            tvRatingScoreDetail.text = item.rate.toString()
            ratingbarDetail.rating = item.rate.div(2).toFloat()
            tvTitleDetail.text = item.name
            btnLikeDetail.apply {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}