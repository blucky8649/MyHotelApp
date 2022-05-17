package com.example.myhotelapp.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.myhotelapp.data.repository.HotelRepository
import com.example.myhotelapp.model.FilterOption
import com.example.myhotelapp.model.Product
import com.example.myhotelapp.utils.Constants.ASC
import com.example.myhotelapp.utils.Constants.ORDER_BY_DATETIME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelListingViewModel @Inject constructor(
    private val repository: HotelRepository
) : ViewModel() {
    private val _orderState = MutableStateFlow(FilterOption(ORDER_BY_DATETIME, ASC))
    private val orderState get() = _orderState as StateFlow<FilterOption>

    @OptIn(ExperimentalCoroutinesApi::class)
    val result = orderState.flatMapLatest {
        repository.getSavedHotelList(it.categoryCode, it.orderCode)
    }
    fun fetchHotelList() = repository.letHotelList().cachedIn(viewModelScope)

    fun setLikeState(item: Product, state: Boolean) = viewModelScope.launch {
        when(state) {
            true -> repository.like(item.copy(likeState = state))
            false -> repository.unLike(item.copy(likeState = state))
        }
    }
}