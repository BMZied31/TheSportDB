package zied.ben.mohamed.fdj.sportdb.features.leagues.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import zied.ben.mohamed.fdj.sportdb.base.BaseActivity
import zied.ben.mohamed.fdj.sportdb.databinding.ActivityMainBinding
import zied.ben.mohamed.fdj.sportdb.features.leagues.presentation.adapters.LeaguesAdapter

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mockList = listOf("ligue1", "ligue2", "liga", "seriaA")

        val adapter = LeaguesAdapter { leagueName ->
            binding.searchView.hide()
            binding.tvLeague.text = leagueName
        }

        binding.recyclerLeagues.adapter = adapter
        binding.searchView.editText.doOnTextChanged { text, _, _, _ ->
            Timber.d("user's input: $text")
            adapter.submitList(
                text?.let { mockList.filter { name -> name.contains(text) } }
                    ?: mockList
            )
        }
    }
}
