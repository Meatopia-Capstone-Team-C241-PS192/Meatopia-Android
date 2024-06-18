package com.example.mygrocerystore.data.database

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mygrocerystore.data.response.MeatResponseItem
import com.example.mygrocerystore.data.retrofit.ApiService

class MeatPage(
    private val pref: DataPreferences,
    private val apiService: ApiService
) : PagingSource<Int, MeatResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MeatResponseItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getUser()?.token.toString()
            if (token.isNotEmpty()) {
                val response = apiService.getMeat("Bearer $token", page, params.loadSize)
                Log.d("MeatPage", "Response: $response")
                if (response.isSuccessful) {
                    val responseData = response.body() ?: emptyList()
                    Log.d("MeatPage", "Response Data: $responseData")
                    LoadResult.Page(
                        data = responseData,
                        prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                        nextKey = if (responseData.isEmpty()) null else page + 1
                    )
                } else {
                    Log.e("MeatPage", "Failed to load meat data: ${response.errorBody()}")
                    LoadResult.Error(Exception("Failed to load meat data"))
                }
            } else {
                Log.e("MeatPage", "Token is empty")
                LoadResult.Error(Exception("Token is empty"))
            }
        } catch (exception: Exception) {
            Log.e("MeatPage", "load() exception: ${exception.message}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MeatResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}
