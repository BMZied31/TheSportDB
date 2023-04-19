package zied.ben.mohamed.fdj.sportdb.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import zied.ben.mohamed.fdj.sportdb.R
import zied.ben.mohamed.fdj.sportdb.databinding.LayoutLoadingBinding
import zied.ben.mohamed.fdj.sportdb.utils.snackBar

/**
 * The base class for all activities in the application.
 * It provides common functionality such as initializing the view binding, displaying a loading view,
 * and handling network connectivity.
 *
 * @param VM the type of the [BaseViewModel] used in the activity
 * @param VB the type of the [ViewBinding] used in the activity
 */
abstract class BaseActivity<out VM : BaseViewModel, VB : ViewBinding> :
    AppCompatActivity() {

    /**
     * The generic view model for the activity.
     */
    protected abstract val viewModel: VM

    /**
     * The root element of the view.
     */
    private val container: ViewGroup by lazy {
        findViewById(android.R.id.content)
    }

    /**
     * The loading view for the activity.
     */
    private val loading: View by lazy {
        LayoutLoadingBinding
            .inflate(LayoutInflater.from(this), container, false)
            .root
    }

    /**
     * The generic view binding for the activity.
     */
    protected lateinit var binding: VB

    /**
     * Initializes the view binding for the activity.
     */
    protected abstract fun getViewBinding(): VB

    /**
     * Called when the activity is created. Initializes the view binding, sets the content view,
     * observes the loading state and error messages, and handles network connectivity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()

        setContentView(binding.root)

        viewModel.loading.observe(this) { loading ->
            showLoading(loading)
        }

        viewModel.error.observe(this) { error ->
            binding.root.snackBar(error ?: getString(R.string.generic_error))
        }
    }

    /**
     * Shows or hides the loading view.
     *
     * @param show a boolean value indicating whether to show or hide the loading view
     */
    private fun showLoading(show: Boolean) {
        container.removeView(loading)
        if (show) container.addView(loading)
    }
}
