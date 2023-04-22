package zied.ben.mohamed.fdj.sportdb.features.leagues.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import zied.ben.mohamed.fdj.sportdb.core.BaseActivity
import zied.ben.mohamed.fdj.sportdb.databinding.ActivityMainBinding
import zied.ben.mohamed.fdj.sportdb.features.leagues.domain.model.LeagueModel
import zied.ben.mohamed.fdj.sportdb.features.leagues.presentation.adapters.LeaguesAdapter
import zied.ben.mohamed.fdj.sportdb.features.leagues.presentation.adapters.TeamsAdapter
import java.util.Locale

/**
 * The main activity of the SportDB app.
 *
 * This class is annotated with [AndroidEntryPoint] to enable Hilt dependency injection.
 * It contains logic for displaying a list of leagues and teams, and handling user input.
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    // Inflates the view binding for the activity using the layoutInflater
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    // Uses the viewModels delegate to lazily initialize the MainViewModel for this activity
    override val viewModel: MainViewModel by viewModels()

    // Adapter for the list of leagues
    private val leaguesAdapter: LeaguesAdapter by lazy {
        LeaguesAdapter { leagueName ->
            binding.searchView.hide()
            viewModel.getTeamsByLeague(leagueName)
        }
    }

    // Adapter for the list of teams
    private val teamsAdapter: TeamsAdapter by lazy {
        TeamsAdapter()
    }

    // The list of all leagues, used for filtering
    private var leaguesList = listOf<LeagueModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the observers and load the list of leagues
        initObservers()
        viewModel.getAllLeagues()

        // Set the adapters for the league and team lists
        binding.recyclerLeagues.adapter = leaguesAdapter
        binding.recyclerTeams.adapter = teamsAdapter

        // Handle changes to the search input field
        binding.searchView.editText.doOnTextChanged { text, _, _, _ ->
            Timber.d("user's input: $text")
            leaguesAdapter.submitList(
                text?.let {
                    leaguesList.filter { league ->
                        league.name.lowercase(Locale.FRANCE)
                            .contains(text.toString().lowercase(Locale.FRANCE))
                    }
                }
                    ?: leaguesList
            )
        }
    }

    /**
     * Initializes the observers for the leagues and teams lists.
     */
    private fun initObservers() {
        viewModel.leagues.observe(this) {
            leaguesList = it
        }

        viewModel.teams.observe(this) {
            teamsAdapter.submitList(it)
        }
    }
}
