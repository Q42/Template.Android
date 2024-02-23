package nl.q42.template.logging

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import nl.q42.template.BuildConfig

/** A value suitable for Crashlytics
 * This value is used to truncate the user-defined message and the exception message
 * In theory, the message sent to Crashlytics could therefore be 2x this value
 */
private const val MAX_CHARS_IN_LOG = 1200

/** A Crashlytics logger. The name Antilog might be an unfortunate choice by the Napier library;
 * this is not a stub
 */
class CrashlyticsAntilog : Antilog() {

    private val logcatAntilog = DebugAntilog()

    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        if (BuildConfig.DEBUG || priority > LogLevel.DEBUG) {
            // also send to logcat
            logcatAntilog.log(priority, tag, throwable, message)
        }

        val limitedMessage = message?.take(MAX_CHARS_IN_LOG) // to avoid OutOfMemoryError's

        if ((priority == LogLevel.INFO || priority == LogLevel.DEBUG || priority == LogLevel.WARNING) && limitedMessage != null) {
            val errorMessage = throwable?.let {
                " with error: ${throwable}: ${throwable.message}".take(MAX_CHARS_IN_LOG)
            } ?: ""
            Firebase.crashlytics.log(limitedMessage + errorMessage)
        }

        if (priority == LogLevel.ERROR) {
            Firebase.crashlytics.log("recordException with message: $limitedMessage")
            Firebase.crashlytics.recordException(throwable ?: Exception(message))
        }
    }
}
