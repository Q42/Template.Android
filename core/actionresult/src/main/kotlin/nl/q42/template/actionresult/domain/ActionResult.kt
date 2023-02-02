package nl.q42.template.actionresult.domain

sealed class ActionResult<out T : Any?> {

    /**
     * An error class used so that feature modules can give an exact error message to the user.
     */
    sealed class Error(open val exception: Exception) : ActionResult<Nothing>() {

        data class UnAuthorized(override val exception: Exception, val message: String?) : Error(exception)

        data class TooManyRequests(override val exception: Exception) : Error(exception)

        data class Cancelled(override val exception: Exception) : Error(exception)

        data class InvalidErrorResponse(
            override val exception: Exception = Exception("API error format is invalid"),
            val httpStatusCode: Int? = null
        ) :
            Error(exception)

        data class ServerError(override val exception: Exception, val message: String) : Error(exception)

        object NotFoundError : Error(Exception("404: Not Found"))

        data class NetworkError(override val exception: Exception) : Error(exception)

        data class Other(override val exception: Exception) : Error(exception)

        object NotImplemented : Error(Exception("API error format not implemented"))
    }

    data class Success<T : Any?>(val result: T) : ActionResult<T>()
}

fun <T> ActionResult<T>.getDataOrNull(): T? = when (this) {
    is ActionResult.Error -> null
    is ActionResult.Success -> result
}

/**
 * Maps an ActionResult with result type S into an ActionResult with result type T.
 *
 * Example usage: `userEntityActionResult.map(UserEntity::mapToUser)`
 */
fun <S, T> ActionResult<S>.map(mapper: (S) -> T): ActionResult<T> = when (this) {
    is ActionResult.Error -> this
    is ActionResult.Success -> ActionResult.Success(mapper(this.result))
}