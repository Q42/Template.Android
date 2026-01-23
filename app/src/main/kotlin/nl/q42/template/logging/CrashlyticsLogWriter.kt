package nl.q42.template.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics

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
                throwable ?: Exception(
                    limitedMessage,
                )
            )
        }
    }

}
