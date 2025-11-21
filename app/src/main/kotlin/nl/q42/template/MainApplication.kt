package nl.q42.template

import android.app.Application
import android.os.StrictMode
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import nl.q42.template.di.initDependencyInjection
import nl.q42.template.logging.CrashlyticsLogger

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = false
            Napier.base(DebugAntilog())

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
        } else {
            FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = true
            Napier.base(CrashlyticsLogger())
        }

        initDependencyInjection(this)
    }
}
