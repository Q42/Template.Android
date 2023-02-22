package nl.q42.template.actionresult.data

import com.haroldadmin.cnradapter.NetworkResponse
import com.squareup.moshi.JsonEncodingException
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CancellationException
import nl.q42.template.actionresult.domain.ActionResult
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val UNAUTHENTICATED_STATUSES = 401
private const val NOT_FOUND_REQUESTS_STATUS = 404
private const val TOO_MANY_REQUESTS_STATUS = 429

private fun isUnAuthorized(httpStatusCode: Int?) = httpStatusCode == UNAUTHENTICATED_STATUSES
private fun isNotFound(httpStatusCode: Int?) = httpStatusCode == NOT_FOUND_REQUESTS_STATUS
private fun isTooManyRequests(httpStatusCode: Int?) = httpStatusCode == TOO_MANY_REQUESTS_STATUS

/**
 * Maps a remote [NetworkResponse] to an [ActionResult] transforming all possible errors and exceptions into our domain error models
 */
suspend fun <T : Any> mapToActionResult(request: suspend () -> NetworkResponse<T, ApiErrorResponse>): ActionResult<T> =
    try {
        request().networkResponseToActionResult()
    } catch (e: Exception) {
        if (e is CancellationException) ActionResult.Error.Cancelled(e) else ActionResult.Error.Other(e)
    }

private fun <T : Any> NetworkResponse<T, ApiErrorResponse>.networkResponseToActionResult(): ActionResult<T> =
    when (this) {
        is NetworkResponse.Success -> ActionResult.Success(this.body)
        is NetworkResponse.ServerError -> {
            val errorMessage: String? = this.body?.message
            val exception = Exception("$errorMessage $this.code")
            when {
                isTooManyRequests(this.code) -> ActionResult.Error.TooManyRequests(exception)
                isUnAuthorized(this.code) -> ActionResult.Error.UnAuthorized(exception, errorMessage)
                // a 404 is not a ServerError, but an UnknownError (mapping to Error.NotFoundError)
                !errorMessage.isNullOrBlank() -> ActionResult.Error.ServerError(exception, errorMessage)
                errorMessage == null -> ActionResult.Error.InvalidErrorResponse(exception, this.code)
                else -> ActionResult.Error.Other(exception)
            }
        }

        is NetworkResponse.NetworkError -> // Used to represent connectivity errors
            when (this.error) {
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> {
                    ActionResult.Error.NetworkError(this.error)
                }

                is JsonEncodingException, is EOFException -> {
                    // let's log this error, includes a corrupt/broken json response:
                    ActionResult.Error.Other(this.error)
                }

                else -> ActionResult.Error.Other(this.error)
            }

        is NetworkResponse.UnknownError -> {
            val statusCode = this.code
            val errorMessage = "Received NetworkResponse.UnknownError with response code $statusCode and header ${this.headers}"
            val exception = IOException(errorMessage, this.error)
            Napier.w(this.error) { "NetworkResponse.UnknownError" }
            when {
                statusCode == null -> {
                    ActionResult.Error.InvalidErrorResponse(exception)
                }

                isTooManyRequests(statusCode) -> {
                    ActionResult.Error.TooManyRequests(exception)
                }

                isNotFound(statusCode) -> ActionResult.Error.NotFoundError
                isUnAuthorized(statusCode) -> ActionResult.Error.UnAuthorized(exception, errorMessage)
                else -> {
                    ActionResult.Error.InvalidErrorResponse(exception, statusCode)
                }
            }
        }
    }
