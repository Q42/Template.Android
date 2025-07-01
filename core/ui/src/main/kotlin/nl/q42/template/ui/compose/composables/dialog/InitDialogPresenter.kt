package nl.q42.template.ui.compose.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.presentation.dialog.DialogPresenter
import nl.q42.template.ui.compose.composables.dialog.Dialog
import nl.q42.widm.core.presentation.dialog.DialogViewState

@Composable
fun InitDialogPresenter(
    dialogPresenter: DialogPresenter,
    onDialogDismissed: (tag: Any) -> Unit = { tag -> dialogPresenter.onDialogDismissed(tag) },
    onDialogConfirmed: (tag: Any) -> Unit = { tag -> dialogPresenter.onDialogConfirmed(tag) }
) {
    val viewState by dialogPresenter.dialogUIState.collectAsStateWithLifecycle(
        initialValue = DialogViewState.None
    )

    val dialogData = (viewState as? DialogViewState.ShowDialog)?.dialogData
    if (dialogData != null)
        Dialog(
            dialog = dialogData,
            onDialogDismissed = onDialogDismissed,
            onDialogConfirmed = onDialogConfirmed,
        )
}
