package zied.ben.mohamed.fdj.sportdb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import zied.ben.mohamed.fdj.sportdb.R
import zied.ben.mohamed.fdj.sportdb.databinding.LayoutLoadingBinding
import zied.ben.mohamed.fdj.sportdb.utils.toast

/**
 * [BaseActivity] the super class for all activities that interact with viewModel in the app
 *
 * @param VM the viewModel of type [BaseViewModel] associated to the activity
 * @param VB the binding of type [ViewBinding] associated to the activity
 */
abstract class BaseActivity<out VM : BaseViewModel, VB : ViewBinding> :
    AppCompatActivity() {

    // Generic viewModel
    protected abstract val viewModel: VM

    // the root element of the view
    private val container: ViewGroup by lazy {
        findViewById(android.R.id.content)
    }

    // Initialize Loading layout
    private val loading: View by lazy {
        LayoutLoadingBinding
            .inflate(LayoutInflater.from(this), container, false)
            .root
    }

    // Generic binding
    protected lateinit var binding: VB

    /**
     * [getViewBinding] associates the viewBinding [VB] of a specific activity of super type [BaseActivity]
     */
    protected abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize viewBinding
        binding = getViewBinding()

        setContentView(binding.root)

        // TODO check internet availability

        // observe loading state
        viewModel.loading.observe(this) { loading ->
            showLoading(loading)
        }

        // Observe error message
        viewModel.error.observe(this) { error ->
            this.toast(error ?: getString(R.string.generic_error))
        }
    }

    /**
     * [showLoading] Add or remove loading view
     *
     * @param show of type [Boolean] either show or hide loading
     */
    private fun showLoading(show: Boolean) {
        container.removeView(loading)
        if (show) container.addView(loading)
    }
}
