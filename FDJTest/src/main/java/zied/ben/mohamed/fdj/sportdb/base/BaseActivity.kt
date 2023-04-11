package zied.ben.mohamed.fdj.sportdb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import zied.ben.mohamed.fdj.sportdb.R
import zied.ben.mohamed.fdj.sportdb.utils.toast

abstract class BaseActivity<VM : BaseViewModel>(private val modelClass: Class<VM>) :
    AppCompatActivity() {

    // TODO use viewBinding
    private val container: ViewGroup by lazy {
        findViewById(android.R.id.content)
    }

    // TODO use viewBinding
    private val loading: View by lazy {
        LayoutInflater.from(this).inflate(R.layout.layout_loading, container, false)
    }

    protected abstract val viewModel : VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loading.observe(this) { loading ->
            showLoading(loading)
        }

        viewModel.error.observe(this) { error ->
            this.toast(error ?: "Unknown Error" )
        }
    }

    private fun showLoading(show: Boolean) {
        container.removeView(loading)
        if (show) container.addView(loading)
    }

    // TODO deprecated
    override fun onBackPressed() {
        if (canGoBack())
            super.onBackPressed()
    }

    abstract fun canGoBack(): Boolean
}