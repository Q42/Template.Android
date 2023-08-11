package nl.q42.template

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import nl.q42.template.logging.CrashlyticsAntilog

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Napier.base(DebugAntilog())
            Napier.d { "TODO Disabling crashlytics for a debug build" }
            // TODO FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)

        } else {
            Napier.d { "TODO Enabling crashlytics for non-debug build" }
            // TODO FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            Napier.base(CrashlyticsAntilog())
        }
    }
}
