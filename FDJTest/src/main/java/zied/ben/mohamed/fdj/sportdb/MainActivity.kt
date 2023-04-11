package zied.ben.mohamed.fdj.sportdb

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import zied.ben.mohamed.fdj.sportdb.base.BaseActivity

@AndroidEntryPoint
class MainActivity: BaseActivity<MainViewModel>(MainViewModel::class.java) {

    override val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun canGoBack(): Boolean {
        return false
    }
}