package zied.ben.mohamed.fdj.sportdb.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * The base class for all view models in the application. This class provides common
 * behaviors that are used across view models.
 */
abstract class BaseViewModel : ViewModel() {

    // LiveData responsible for the display of the loading view
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // LiveData responsible for the display of errors
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Posts the value to [_loading] mutableLiveData. This method is called inside
     * the view models that extend [BaseViewModel].
     *
     * @param isLoading Whether to show or hide the loading view.
     */
    protected fun toggleLoading(isLoading: Boolean) {
        _loading.postValue(isLoading)
    }

    /**
     * Posts the value to [_error] mutableLiveData. This method is called inside
     * the viewmodels that extend [BaseViewModel].
     *
     * @param message The error message to show.
     */
    protected fun showError(message: String?) {
        _error.postValue(message)
    }
}
