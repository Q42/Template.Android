package nl.q42.template

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import nl.q42.template.logging.CrashlyticsAntilog

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
            Napier.base(DebugAntilog())
        } else {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            Napier.base(CrashlyticsAntilog())
        }
    }
}
