package nl.q42.template.core.network.logger

import co.touchlab.kermit.Logger
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject

/**
 * Logger with pretty json logging.
 */
class JsonFormattedHttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) try {
            Logger.d { JSONObject(message).toString(4) }
        } catch (e: JSONException) {
            Logger.d { message }
        }
        else Logger.d { message }
    }
}
