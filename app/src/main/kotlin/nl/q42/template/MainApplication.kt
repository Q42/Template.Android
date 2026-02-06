package nl.q42.template

import android.app.Application
import android.os.StrictMode
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import nl.q42.template.di.initDependencyInjection
import nl.q42.template.logging.CrashlyticsLogWriter

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.setTag("Template")

        if (BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = false

            Logger.setMinSeverity(Severity.Verbose)
            Logger.setLogWriters(LogcatWriter())

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

            Logger.setMinSeverity(Severity.Info)
            Logger.setLogWriters(
                LogcatWriter(),
                CrashlyticsLogWriter()
            )
        }

        initDependencyInjection(this)
    }
}
