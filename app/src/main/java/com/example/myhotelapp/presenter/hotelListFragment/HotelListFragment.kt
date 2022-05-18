package com.example.myhotelapp.presenter.hotelListFragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhotelapp.R
import com.example.myhotelapp.databinding.FragmentHotelListBinding
import com.example.myhotelapp.presenter.HotelListAdapter
import com.example.myhotelapp.presenter.HotelListingViewModel
import com.example.myhotelapp.utils.Constants.ASC
import com.example.myhotelapp.utils.Constants.DESC
import com.example.myhotelapp.utils.Constants.ORDER_BY_DATETIME
import com.example.myhotelapp.utils.Constants.ORDER_BY_RATING
import com.example.myhotelapp.utils.Constants.PAGE_SIZE
import com.example.myhotelapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HotelListFragment: Fragment() {

    private var _binding: FragmentHotelListBinding? = null
    val binding get() = _binding!!

    val viewModel: HotelListingViewModel by activityViewModels()

    private val hotelAdapter by lazy {
        HotelListAdapter(viewModel)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHotelListBinding.inflate(layoutInflater, container, false)
        collectEndPageState()
        collectProductState()
        setupRecyclerView()
        setupSwipeRefresh()
        hotelAdapter.setOnItemClickListener {
            val action = HotelListFragmentDirections.actionHotelListFragmentToHotelDetailFragment(it)
            findNavController().navigate(action)
        }
        return binding.root
    }
    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = hotelAdapter
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = null
            addOnScrollListener(scrollListener)
            setPadding(0, 0, 0, 200)
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

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
        isLoading = true
    }
    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
        binding.progressBarMain.isVisible = false
        isLoading = false
    }
    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                viewModel.handleProductList(true)
                isRefreshing = false
            }
        }

    }
    private fun collectProductState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productState.collect { result ->
                when(result) {
                    is Resource.Success -> {
                        result.data?.let {
                            hideProgressBar()
                            hotelAdapter.submitList(it.toList())
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        Toast.makeText(activity, "${result.message}", Toast.LENGTH_LONG).show()
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                        binding.progressBar.isVisible = true
                    }
                }

            }
        }
    }
    private fun collectEndPageState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isEndReached.collectLatest { isEndReached ->
                isLastPage = isEndReached
            }
        }
    }
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                isScrolling = true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndIsNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= PAGE_SIZE
            val shouldPaginate = isNotLoadingAndIsNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling

            if (isAtLastItem && isLastPage) {
                // 아이템이 전부 로드된 상태에서 스크롤 포지션이 리사이클러뷰 마지막이라면 패딩을 좁힌다.
                binding.recyclerView.setPadding(0, 0, 0, 0)
            }
            if (shouldPaginate) {
                viewModel.handleProductList(false)
                isScrolling = false
            }
        }
    }
}