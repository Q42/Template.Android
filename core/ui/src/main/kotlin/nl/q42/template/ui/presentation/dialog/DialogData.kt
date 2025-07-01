package nl.q42.template.ui.presentation.dialog

import nl.q42.template.ui.presentation.ViewStateString

data class DialogData(
    val title: ViewStateString? = null,
    val description: ViewStateString,
    val confirmButtonTitle: ViewStateString? = null,
    val dismissButtonTitle: ViewStateString? = null,
    /** Can be used to identify this dialog, when a screen shows multiple dialogs */
    val tag: Any = DefaultTag
) {
    companion object {
        val DefaultTag = Unit
    }
}
