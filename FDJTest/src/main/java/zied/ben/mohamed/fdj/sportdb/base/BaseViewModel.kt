package zied.ben.mohamed.fdj.sportdb.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        toggleLoading(isLoading = false)
    }

    protected fun toggleLoading(isLoading: Boolean) {
        _loading.postValue(isLoading)
    }

    protected fun showError(message: String?) {
        _error.postValue(message)
    }

}