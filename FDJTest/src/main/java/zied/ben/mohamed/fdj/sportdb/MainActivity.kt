package zied.ben.mohamed.fdj.sportdb

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import zied.ben.mohamed.fdj.sportdb.base.BaseActivity
import zied.ben.mohamed.fdj.sportdb.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvHello.text = "First Step 1 Complete :)"
    }

}