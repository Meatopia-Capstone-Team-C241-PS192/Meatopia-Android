import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mygrocerystore.data.database.DataPreferences
import com.example.mygrocerystore.data.database.Repository
import com.example.mygrocerystore.data.response.MeatResponseItem
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val dataPreferences: DataPreferences,
    private val application: Application
) : ViewModel() {
    private val repository = Repository(application, dataPreferences)

    val getListMeat: LiveData<PagingData<MeatResponseItem>> =
        repository.listMeat().cachedIn(viewModelScope)

    fun searchMeats(query: String): Flow<PagingData<MeatResponseItem>> {
        return repository.searchMeats(query).cachedIn(viewModelScope)
    }
}
