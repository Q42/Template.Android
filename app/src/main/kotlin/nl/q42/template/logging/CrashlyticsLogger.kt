package nl.q42.template.logging

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.LogLevel
import nl.q42.template.BuildConfig
import io.github.aakira.napier.Napier

/** A value suitable for Crashlytics
 * This value is used to truncate the user-defined message and the exception message
 * In theory, the message sent to Crashlytics could therefore be 2x this value
 */
private const val MAX_CHARS_IN_LOG = 1200
private const val DEFAULT_TAG = "AppLogger"

/** A Crashlytics logger. The name Antilog might be an unfortunate choice by the Napier library;
 * this is not a stub
 */
class CrashlyticsLogger : Antilog() {
    override fun performLog(
        priority: LogLevel,
        tag: String?,
        throwable: Throwable?,
        message: String?
    ) {
        if (message == null && throwable == null) return

        val safeTag = tag ?: DEFAULT_TAG

        if (BuildConfig.DEBUG || priority > LogLevel.DEBUG) {
            logToLogcat(priority, safeTag, message, throwable)
        }

        val limitedMessage = message?.take(MAX_CHARS_IN_LOG) ?: "(no message)" // to avoid OutOfMemoryError's

        // at least one of message or throwable is not null
        if (priority < LogLevel.ERROR) {
            val errorMessage = throwable?.let {
                " with error: ${throwable}: ${throwable.message}".take(MAX_CHARS_IN_LOG)
            } ?: ""
            Firebase.crashlytics.log(limitedMessage + errorMessage)
        } else {
            Firebase.crashlytics.log("recordException with message: $limitedMessage")
            Firebase.crashlytics.recordException(
                throwable ?: buildCrashlyticsSyntheticException(
                    limitedMessage,
                    safeTag
                )
            )
        }
    }

    private fun logToLogcat(
        priority: LogLevel,
        tag: String,
        message: String?,
        throwable: Throwable?
    ) {
        val msg = message ?: ""
        when (priority) {
            LogLevel.VERBOSE -> Log.v(tag, msg, throwable)
            LogLevel.DEBUG -> Log.d(tag, msg, throwable)
            LogLevel.INFO -> Log.i(tag, msg, throwable)
            LogLevel.WARNING -> Log.w(tag, msg, throwable)
            LogLevel.ERROR -> Log.e(tag, msg, throwable)
            LogLevel.ASSERT -> Log.wtf(tag, msg, throwable)
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
            Log.e(tag, "Got unexpected stacktrace while logging a message: ${stackTrace.contentToString()}")
            return SyntheticException(message, stackTrace)
        }
        if (lastToRemove.className != Napier::class.java.name || lastToRemove.methodName != "e\$default") {
            Log.e(
                tag,
                "Got unexpected stacktrace: class: ${lastToRemove.className}, method: ${lastToRemove.methodName}"
            )
        }
        val abbreviatedStackTrace = stackTrace.takeLast(stackTrace.size - numToRemove).toTypedArray()
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
