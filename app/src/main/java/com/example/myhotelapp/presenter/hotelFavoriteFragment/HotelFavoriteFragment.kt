package com.example.myhotelapp.presenter.hotelFavoriteFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhotelapp.R
import com.example.myhotelapp.databinding.FragmentHotelListBinding
import com.example.myhotelapp.presenter.HotelListingViewModel
import com.example.myhotelapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotelFavoriteFragment: Fragment() {

    private var _binding: FragmentHotelListBinding? = null
    val binding get() = _binding!!

    val viewModel: HotelListingViewModel by viewModels()

    private val hotelAdapter: HotelListSavedAdapter by lazy {
        HotelListSavedAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHotelListBinding.inflate(layoutInflater, container, false)
        setupRecyclerView()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.result.collectLatest {
                hotelAdapter.submitList(it)
            }
        }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_order_seq -> {
                showOrderingPopUpMenu()
                true
            }
            R.id.menu_order_category -> {
                showOrderingCatetoryPopUpMenu()
                true
            }
            else -> {
                findNavController().navigateUp()
                true
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.favorite).isVisible = false
    }

    private fun showOrderingPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_order_seq) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.order_products, menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.desc -> {
                        viewModel.setOrdering(Constants.DESC)
                    }
                    R.id.asc -> {
                        viewModel.setOrdering(Constants.ASC)
                    }
                }
                true
            }
            show()
        }

    }

    private fun showOrderingCatetoryPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_order_category) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.order_products_category, menu)

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.toTime -> {
                        viewModel.setOrderingByCategory(Constants.ORDER_BY_DATETIME)
                    }
                    R.id.toRaging -> {
                        viewModel.setOrderingByCategory(Constants.ORDER_BY_RATING)
                    }
                }
                true
            }
            show()
        }

    }
}