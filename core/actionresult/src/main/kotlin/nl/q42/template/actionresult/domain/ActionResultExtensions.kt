package nl.q42.template.actionresult.domain

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

/**
 * Maps an ActionResult with result type List<S> into an ActionResult with result type List<T>.
 *
 * Example usage: `userEntityActionResult.mapList(UserEntity::mapToUser)`
 */
fun <S, T> ActionResult<List<S>>.mapList(mapper: (S) -> T): ActionResult<List<T>> = when (this) {
    is ActionResult.Error -> this
    is ActionResult.Success -> ActionResult.Success(this.result.map { mapper(it) })
}
