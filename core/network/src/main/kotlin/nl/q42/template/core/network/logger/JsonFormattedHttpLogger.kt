package nl.q42.template.core.network.logger

import io.github.aakira.napier.Napier
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject

/**
 * Logger with pretty json logging.
 */
class JsonFormattedHttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                Napier.d { JSONObject(message).toString(4) }
            } catch (e: JSONException) {
                Napier.d { message }
            }
        } else {
            Napier.d { message }
        }
    }
}
