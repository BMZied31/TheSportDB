package zied.ben.mohamed.fdj.sportdb.features.leagues.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import zied.ben.mohamed.fdj.sportdb.core.BaseViewModel
import zied.ben.mohamed.fdj.sportdb.core.FDJResult
import zied.ben.mohamed.fdj.sportdb.di.IoDispatcher
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase.GetLeaguesUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLeaguesUseCase: GetLeaguesUseCase,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : BaseViewModel() {

    private val _leagues = MutableLiveData<List<LeagueModel>>()
    val leagues: LiveData<List<LeagueModel>> = _leagues

    fun getAllLeagues() {
        toggleLoading(isLoading = true)
        viewModelScope.launch(dispatcherIO) {
            getLeaguesUseCase.invoke().collect { result ->
                when (result) {
                    is FDJResult.Success -> _leagues.postValue(result.data)
                    is FDJResult.Failure -> showError(result.error?.message)
                }
            }
            toggleLoading(isLoading = false)
        }
    }
}
