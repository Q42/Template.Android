package nl.q42.template.logging

import android.util.Log
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import io.github.aakira.napier.Napier

/** A value suitable for Crashlytics
 * This value is used to truncate the user-defined message and the exception message
 * In theory, the message sent to Crashlytics could therefore be 2x this value
 */
private const val MAX_CHARS_IN_LOG = 1200

/**
 * A Crashlytics logger. Logs Severity.Error as Non-Fatal and less severe messages will be added
 * to the next crashlytics event (crash, non-fatal or ANR) as breadcrumbs
 */
class CrashlyticsLogWriter : LogWriter() {
    override fun log(
        severity: Severity,
        message: String,
        tag: String,
        throwable: Throwable?
    ) {

        val limitedMessage =
            message.take(MAX_CHARS_IN_LOG) // to avoid OutOfMemoryError's

        if (severity < Severity.Error) {
            val errorMessage = throwable?.let {
                " with error: ${throwable}: ${throwable.message}".take(MAX_CHARS_IN_LOG)
            } ?: ""
            Firebase.crashlytics.log(limitedMessage + errorMessage)
        } else {
            Firebase.crashlytics.log("recordException with message: $limitedMessage")
            Firebase.crashlytics.recordException(
                throwable ?: buildCrashlyticsSyntheticException(
                    limitedMessage,
                    tag
                )
            )
        }
    }

    /** Strip the stacktrace so that the calls implementing error logging are removed and the actual
     * code that called Napier.e() remains.
     *
     * This is a workaround for the fact that Crashlytics groups errors by stacktrace
     * [https://stackoverflow.com/a/59779764](https://stackoverflow.com/a/59779764)
     */
    private fun buildCrashlyticsSyntheticException(message: String, tag: String): Exception {
        val stackTrace = Thread.currentThread().stackTrace
        val numToRemove = 9
        val lastToRemove = stackTrace.getOrNull(numToRemove - 1)
        if (lastToRemove == null) {
            Log.e(
                tag,
                "Got unexpected stacktrace while logging a message: ${stackTrace.contentToString()}"
            )
            return SyntheticException(message, stackTrace)
        }
        if (lastToRemove.className != Napier::class.java.name || lastToRemove.methodName != "e\$default") {
            Log.e(
                tag,
                "Got unexpected stacktrace: class: ${lastToRemove.className}, method: ${lastToRemove.methodName}"
            )
        }
        val abbreviatedStackTrace =
            stackTrace.takeLast(stackTrace.size - numToRemove).toTypedArray()
        return SyntheticException(message, abbreviatedStackTrace)
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
