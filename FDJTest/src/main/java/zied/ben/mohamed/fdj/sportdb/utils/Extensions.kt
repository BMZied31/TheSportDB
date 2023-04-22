package zied.ben.mohamed.fdj.sportdb.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * Displays a [Snackbar] with the given [message] on the [View].
 *
 * @param message the message to display on the Snackbar
 */
fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}
