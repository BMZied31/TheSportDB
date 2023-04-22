package zied.ben.mohamed.fdj.sportdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main application class for the SportDB app.
 * It's annotated with [HiltAndroidApp] to initializes Hilt dependency injection component for the app.
 */
@HiltAndroidApp
class SportDBApplication : Application()
