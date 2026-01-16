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

        if (BuildConfig.DEBUG || priority > LogLevel.DEBUG) {
            // also send to logcat
            val logLevel = priority.toAndroidLogLevel()
            val logMessage = buildString {
                if (message != null) append(message)
                if (throwable != null) {
                    if (message != null) append("\n")
                    append(Log.getStackTraceString(throwable))
                }
            }

            Log.println(logLevel, tag ?: "AppLogger", logMessage)
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
        val numToRemove = 9
        val lastToRemove = stackTrace.getOrNull(numToRemove - 1)
        if (lastToRemove == null) {
            Log.e(
                null,
                "Got unexpected stacktrace while logging a message: ${stackTrace.contentToString()}"
            )
            return SyntheticException(message, stackTrace)
        }
        if (lastToRemove.className != Napier::class.java.name || lastToRemove.methodName != "e\$default") {
            Log.e(
                null,
                "Got unexpected stacktrace: class: ${lastToRemove.className}, method: ${lastToRemove.methodName}"
            )
        }
        val abbreviatedStackTrace = stackTrace.takeLast(stackTrace.size - numToRemove).toTypedArray()
        return SyntheticException(message, abbreviatedStackTrace)
    }

    private fun LogLevel.toAndroidLogLevel(): Int {
        return when (this) {
            LogLevel.VERBOSE -> Log.VERBOSE
            LogLevel.DEBUG -> Log.DEBUG
            LogLevel.INFO -> Log.INFO
            LogLevel.WARNING -> Log.WARN
            LogLevel.ERROR -> Log.ERROR
            LogLevel.ASSERT -> Log.ASSERT
        }
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
