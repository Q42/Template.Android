package nl.q42.template.actionresult.data

import com.squareup.moshi.JsonClass

/**
 * ErrorResponse used by NetworkResponseAdapter. Config it to match your server's error
 * json structure.
 *
 * More info: https://haroldadmin.github.io/NetworkResponseAdapter/
 */
@JsonClass(generateAdapter = true)
data class ApiErrorResponse(val message: String)
