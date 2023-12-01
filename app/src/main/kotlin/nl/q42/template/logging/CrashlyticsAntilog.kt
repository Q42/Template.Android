package nl.q42.template.logging

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel

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
