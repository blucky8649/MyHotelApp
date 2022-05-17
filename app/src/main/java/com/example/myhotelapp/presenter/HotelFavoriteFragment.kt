package com.example.myhotelapp.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhotelapp.databinding.FragmentHotelListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HotelFavoriteFragment: Fragment() {

    private var _binding: FragmentHotelListBinding? = null
    val binding get() = _binding!!

    val viewModel:HotelListingViewModel by viewModels()

    private val hotelAdapter: HotelListAdapter by lazy {
        HotelListAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHotelListBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()

        return binding.root
    }
    fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = hotelAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}