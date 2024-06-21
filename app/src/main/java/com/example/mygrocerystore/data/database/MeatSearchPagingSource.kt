import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.response.MeatResponseItem
import com.example.mygrocerystore.data.retrofit.ApiService
import retrofit2.HttpException
import java.io.IOException

class MeatSearchPagingSource(
    private val apiService: ApiService,
    private val pref: DataPreferences,
    private val query: String
) : PagingSource<Int, MeatResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MeatResponseItem> {
        val page = params.key ?: 1
        return try {
            val token = pref.getUser()?.token.toString()
            val response = apiService.searchMeat("Bearer $token", query, page, params.loadSize)
            Log.d("MeatSearchPagingSource", "Query: $query, Page: $page, LoadSize: ${params.loadSize}")
            Log.d("MeatSearchPagingSource", "Response code: body: $response")

            if (response.isNotEmpty()) {
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (response.isEmpty()) null else page + 1
                )
            } else {
                Log.e("MeatSearchPagingSource", "Received null response from API")
                LoadResult.Error(NullPointerException("Response body was null"))
            }
        } catch (exception: IOException) {
            Log.e("MeatSearchPagingSource", "IOException: ${exception.message}")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("MeatSearchPagingSource", "HttpException: ${exception.message()}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MeatResponseItem>): Int? {
        return state.anchorPosition
    }
}
