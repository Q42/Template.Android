package nl.q42.template.actionresult.data

import nl.q42.template.actionresult.domain.ActionResult

/**
 * Shortcut to react on success and error states of an action. Because this looks sexier in the code.
 */
suspend fun <T> handleAction(action: ActionResult<T>, onSuccess: suspend (T) -> Unit, onError: suspend (ActionResult.Error) -> Unit) {
    when (action) {
        is ActionResult.Success -> onSuccess(action.result)
        is ActionResult.Error -> onError(action)
    }
}
