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
        if (message == null && throwable == null) return

        if (BuildConfig.DEBUG || priority > LogLevel.DEBUG) {
            // also send to logcat
            logcatAntilog.log(priority, tag, throwable, message)
        }

        val limitedMessage = message?.take(MAX_CHARS_IN_LOG)  ?: "(no message)" // to avoid OutOfMemoryError's

        // at least one of message or throwable is not null
        if (priority < LogLevel.ERROR) {
            val errorMessage = throwable?.let {
                " with error: ${throwable}: ${throwable.message}".take(MAX_CHARS_IN_LOG)
            } ?: ""
            Firebase.crashlytics.log(limitedMessage + errorMessage)
        } else {
            Firebase.crashlytics.log("recordException with message: $limitedMessage")
            Firebase.crashlytics.recordException(throwable ?: buildCrashlyticsSyntheticException(limitedMessage))
        }
    }

    /** Strip the stacktrace so that the calls implementing error logging are removed and the actual
     * code that called Napier.e() remains.
     *
     * This is a workaround for the fact that Crashlytics groups errors by stacktrace
     * [https://stackoverflow.com/a/59779764](https://stackoverflow.com/a/59779764)
     */
    private fun buildCrashlyticsSyntheticException(message: String): Exception {
        val stackTrace = Thread.currentThread().stackTrace
        val numToRemove = 4
        val lastToRemove = stackTrace[numToRemove - 1]
        if (lastToRemove.className != "io.github.aakira.napier.Napier" || lastToRemove.methodName != "e\$default"){
            logcatAntilog.log(priority = LogLevel.ERROR, tag = null, throwable = null,
                    message = "Got unexpected stacktrace: class: ${lastToRemove.className}, method: ${lastToRemove.methodName}"
            )
        }
        val abbreviatedStackTrace = stackTrace.takeLast(stackTrace.size - numToRemove).toTypedArray()
        return SyntheticException("Synthetic Exception: $message", abbreviatedStackTrace)
    }

}

class SyntheticException(
        message: String,
        private val abbreviatedStackTrace: Array<StackTraceElement>
) : Exception(message) {
    override fun getStackTrace(): Array<StackTraceElement> {
        return abbreviatedStackTrace
    }
}
