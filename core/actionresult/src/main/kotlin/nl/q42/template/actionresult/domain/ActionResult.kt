package nl.q42.template.actionresult.domain

sealed class ActionResult<out T : Any?> {

    /**
     * An error class used so that feature modules can give an exact error message to the user.
     */
    sealed class Error(open val throwable: Throwable) : ActionResult<Nothing>() {

        data class UnAuthorized(override val throwable: Throwable, val message: String?) : Error(throwable)

        data class TooManyRequests(override val throwable: Throwable) : Error(throwable)

        data class Cancelled(override val throwable: Throwable) : Error(throwable)

        data class InvalidErrorResponse(
            override val throwable: Throwable = Throwable("API error format is invalid"),
            val httpStatusCode: Int? = null
        ) :
            Error(throwable)

        data class ServerError(override val throwable: Throwable, val message: String) : Error(throwable)

        data object NotFoundError : Error(Exception("404: Not Found"))

        data class NetworkError(override val throwable: Throwable) : Error(throwable)

        data class Other(override val throwable: Throwable) : Error(throwable)

        data object NotImplemented : Error(Throwable("API error format not implemented"))
    }

    data class Success<T : Any?>(val data: T) : ActionResult<T>()
}
