package zied.ben.mohamed.fdj.sportdb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import zied.ben.mohamed.fdj.sportdb.databinding.LayoutLoadingBinding
import zied.ben.mohamed.fdj.sportdb.utils.toast

abstract class BaseActivity<out VM : BaseViewModel, VB : ViewBinding> :
    AppCompatActivity() {

    // TODO use viewBinding
    private val container: ViewGroup by lazy {
        findViewById(android.R.id.content)
    }

    private val loading: View by lazy {
        LayoutLoadingBinding.inflate(LayoutInflater.from(this), container, false).root
    }

    protected abstract val viewModel : VM

    protected lateinit var binding: VB
    protected abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)

        // TODO check internet availability

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