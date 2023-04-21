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
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.TeamModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase.GetLeaguesUseCase
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.usecase.GetTeamsByLeagueUseCase
import javax.inject.Inject

/**
 * ViewModel for the main screen.
 * This ViewModel is responsible for getting the list of leagues and teams by league
 * from the use cases and updating the LiveData objects accordingly.
 *
 * @param getLeaguesUseCase The useCase responsible fro emitting the leagues list
 * @param getTeamsByLeagueUseCase The useCase responsible fro emitting the teams by league list
 * @param dispatcherIO The dispatcher used in viewModelScope
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLeaguesUseCase: GetLeaguesUseCase,
    private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) : BaseViewModel() {

    // LiveData object for the list of leagues
    private val _leagues = MutableLiveData<List<LeagueModel>>()
    val leagues: LiveData<List<LeagueModel>> = _leagues

    // LiveData object for the list of teams
    private val _teams = MutableLiveData<List<TeamModel>>()
    val teams: LiveData<List<TeamModel>> = _teams

    /**
     * This function invokes the corresponding use case to get the list of leagues,
     * and updates the LiveData object [_leagues] accordingly.
     */
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

    /**
     * This function invokes the corresponding use case to get the list of teams for the given league name,
     * and updates the LiveData object [_teams] accordingly.
     * @param leagueName the name of the league for which to get the list of teams.
     */
    fun getTeamsByLeague(leagueName: String) {
        toggleLoading(isLoading = true)
        viewModelScope.launch(dispatcherIO) {
            getTeamsByLeagueUseCase.invoke(leagueName).collect { result ->
                when (result) {
                    is FDJResult.Success -> _teams.postValue(result.data)
                    is FDJResult.Failure -> showError(result.error?.message)
                }
            }
            toggleLoading(isLoading = false)
        }
    }
}
