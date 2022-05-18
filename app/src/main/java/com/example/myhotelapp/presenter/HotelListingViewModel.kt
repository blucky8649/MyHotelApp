package com.example.myhotelapp.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myhotelapp.data.repository.HotelRepository
import com.example.myhotelapp.model.FilterOption
import com.example.myhotelapp.model.Product
import com.example.myhotelapp.model.entitiies.ProductSaveEntity
import com.example.myhotelapp.utils.Constants.ASC
import com.example.myhotelapp.utils.Constants.ORDER_BY_DATETIME
import com.example.myhotelapp.utils.Resource
import com.example.myhotelapp.utils.toProduct
import com.example.myhotelapp.utils.toProductListEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HotelListingViewModel @Inject constructor(
    private val repository: HotelRepository
) : ViewModel() {
    private val _orderState = MutableStateFlow(FilterOption(ORDER_BY_DATETIME, ASC))
    private val orderState get() = _orderState as StateFlow<FilterOption>

    private var _productState = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val productState get() = _productState as StateFlow<Resource<List<Product>>>

    private val _isEndReached = MutableStateFlow(false)
    val isEndReached get() = _isEndReached as StateFlow<Boolean>

    var currentPageNum = 1

    init {
        getData()
        handleProductList(true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val result = orderState.flatMapLatest {
        repository.getSavedHotelList(it.categoryCode, it.orderCode)
    }

    fun setLikeState(item: Product, state: Boolean) = viewModelScope.launch {
        when(state) {
            true -> repository.like(item.copy(likeState = state, time = Date().time))
            false -> repository.unLike(item.copy(likeState = state, time = null))
        }
    }

    fun setOrdering(orderCode: Int) = viewModelScope.launch {
        _orderState.emit(orderState.value.copy(orderCode = orderCode))
    }
    fun setOrderingByCategory(categoryCode: Int) = viewModelScope.launch {
        _orderState.emit(orderState.value.copy(categoryCode = categoryCode))
    }

    fun handleProductList(
        isRefresh: Boolean
    ) = CoroutineScope(Dispatchers.IO).launch {
        _productState.emit(Resource.Loading())
        if (isRefresh) {
            repository.clear()
            currentPageNum = 1
            _isEndReached.emit(false)
        }
        if(isEndReached.value) return@launch

        val response = repository.getRemoteHotelList(currentPageNum++)

        if (response.code == 200 || response.msg == "성공") {
            val cachedList = response.data.product.map { product ->
                val state = async { repository.getLikeState(product.id) }
                product.copy(likeState = state.await())
            }.toList()
            repository.insertAll(cachedList)
            val count = repository.getCount()
            Log.d("Count", count.toString())
            if (count >= response.data.totalCount) {
                _isEndReached.emit(true) // 아이템의 끝 지점 도달
            }
        } else {
            _productState.emit(Resource.Error("오류가 발생하였습니다. RequestCode: ${response.code}"))
        }
    }

    fun getData() = viewModelScope.launch {
        repository.getProductListFlow().collectLatest { productList ->
            _productState.emit(Resource.Success(productList))
        }
    }
}