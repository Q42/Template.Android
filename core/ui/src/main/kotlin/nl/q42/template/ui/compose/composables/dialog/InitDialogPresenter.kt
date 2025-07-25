package nl.q42.template.ui.compose.composables.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.ui.presentation.dialog.DialogPresenter
import nl.q42.template.ui.presentation.dialog.DialogViewState

@Composable
fun InitDialogPresenter(
    dialogPresenter: DialogPresenter,
    onDialogDismissed: (tag: Any) -> Unit = { tag -> dialogPresenter.onDialogDismissed(tag) },
    onDialogConfirmed: (tag: Any) -> Unit = { tag -> dialogPresenter.onDialogConfirmed(tag) }
) {
    val viewState by dialogPresenter.dialogUIState.collectAsStateWithLifecycle(
        initialValue = DialogViewState.None
    )

    val data = (viewState as? DialogViewState.ShowDialog)?.data
    if (data != null)
        Dialog(
            data = data,
            onDismissed = onDialogDismissed,
            onConfirmed = onDialogConfirmed,
        )
}
