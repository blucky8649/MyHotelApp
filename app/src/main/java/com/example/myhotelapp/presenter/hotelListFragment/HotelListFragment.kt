package com.example.myhotelapp.presenter.hotelListFragment

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhotelapp.R
import com.example.myhotelapp.databinding.FragmentHotelListBinding
import com.example.myhotelapp.presenter.HotelListingViewModel
import com.example.myhotelapp.utils.Constants.ASC
import com.example.myhotelapp.utils.Constants.DESC
import com.example.myhotelapp.utils.Constants.ORDER_BY_DATETIME
import com.example.myhotelapp.utils.Constants.ORDER_BY_RATING
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotelListFragment: Fragment() {

    private var _binding: FragmentHotelListBinding? = null
    val binding get() = _binding!!

    val viewModel: HotelListingViewModel by activityViewModels()
    val loadStateAdapter: LoaderStateAdapter by lazy {
        LoaderStateAdapter { hotelAdapter.retry() }
    }
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchHotelList().collectLatest {
                hotelAdapter.submitData(it)
            }
        }

        return binding.root
    }
    fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = hotelAdapter.withLoadStateFooter(loadStateAdapter)
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
            R.id.favorite -> {
                findNavController().navigate(R.id.action_hotelListFragment_to_hotelFavoriteFragment)
                true
            }
            else -> { true }
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu){
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.menu_order_seq).isVisible = false
        menu.findItem(R.id.menu_order_category).isVisible = false
    }

}