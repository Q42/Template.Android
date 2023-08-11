package nl.q42.template.logging

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier

/** A value suitable for Crashlytics */
private const val MAX_CHARS_IN_LOG = 1200

class CrashlyticsAntilog : Antilog() {

    private val logcatAntilog = DebugAntilog()

    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        // also send to logcat
        logcatAntilog.log(priority, tag, throwable, message)

        val limitedMessage = message?.take(MAX_CHARS_IN_LOG) // to avoid OutOfMemoryError's

        // TODO Add crashlytics to your project with this guide https://firebase.google.com/docs/crashlytics/get-started?platform=android
        // and then remove this line and uncomment the others
        Napier.d { "REPLACE_ME no crash logging handler installed. ${ limitedMessage ?: ""}" }

//        if ((priority == LogLevel.INFO || priority == LogLevel.DEBUG || priority == LogLevel.WARNING) && limitedMessage != null) {
//            val errorMessage = throwable?.let {
//                " with error: ${throwable}: ${throwable.message}".take(MAX_CHARS_IN_LOG)
//            } ?: ""
//            Firebase.crashlytics.log(limitedMessage + errorMessage)
//        }
//
//        if (priority < LogLevel.ERROR) {
//            Firebase.crashlytics.log("recordException with message: $limitedMessage")
//            Firebase.crashlytics.recordException(throwable ?: Exception(message))
//        }
//
//
//        throwable?.let {
//            FirebaseCrashlytics.getInstance().recordException(it)
//        }
    }
}
