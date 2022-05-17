package com.example.myhotelapp.data.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myhotelapp.data.remote.HotelApi
import com.example.myhotelapp.model.Data
import com.example.myhotelapp.model.Product
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class HotelPagingSource(val api: HotelApi) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val page = params.key ?: 1
            val response = api.getHotelInfo(page)
            Log.d("PagingAdapter", "${response} 아아ㅏㄴ어림ㄴ $page")
            LoadResult.Page(
                if (response.code == 200 || response.msg == "성공") response.data.product else listOf(),
                prevKey = null,
                nextKey = if (response.code != 200) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}