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
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainViewModel by viewModels()

    private val leaguesAdapter: LeaguesAdapter by lazy {
        LeaguesAdapter { leagueName ->
            binding.searchView.hide()
            binding.tvLeague.text = leagueName
        }
    }

    private var leaguesList = listOf<LeagueModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
        viewModel.getAllLeagues()

        binding.recyclerLeagues.adapter = leaguesAdapter
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

    private fun initObservers() {
        viewModel.leagues.observe(this) {
            leaguesList = it
        }
    }
}
