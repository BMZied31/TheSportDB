package zied.ben.mohamed.fdj.sportdb.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * [BaseViewModel] The Super class for all viewModels in the application.
 * It regroups all the common behaviors for viewModels.
 */
abstract class BaseViewModel : ViewModel() {

    // Loading liveData responsible for the display of the loading view
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Error liveData responsible for the display of errors
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * initiate every viewModel with loading turn off.
     */
    init {
        toggleLoading(isLoading = false)
    }

    /**
     * [toggleLoading] posts the value to [_loading] mutableLiveData
     * The method is called inside the viewModels that extends [BaseViewModel]
     *
     * @param isLoading of type [Boolean]
     */
    protected fun toggleLoading(isLoading: Boolean) {
        _loading.postValue(isLoading)
    }

    /**
     * [showError] posts the value to [_error] mutableLiveData
     * The method is called inside the viewModels that extends [BaseViewModel]
     *
     * @param message of type [String?]
     */
    protected fun showError(message: String?) {
        _error.postValue(message)
    }
}
