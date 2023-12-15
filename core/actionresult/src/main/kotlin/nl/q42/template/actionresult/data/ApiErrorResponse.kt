package nl.q42.template.actionresult.data

import kotlinx.serialization.Serializable


/**
 * ErrorResponse used by NetworkResponseAdapter. Config it to match your server's error
 * json structure.
 *
 * More info: https://haroldadmin.github.io/NetworkResponseAdapter/
 */
@Serializable
data class ApiErrorResponse(val message: String)
