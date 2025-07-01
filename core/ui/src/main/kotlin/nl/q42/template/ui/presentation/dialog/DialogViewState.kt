package nl.q42.widm.core.presentation.dialog

import nl.q42.template.ui.presentation.dialog.DialogData

sealed class DialogViewState {
    data class ShowDialog(val dialogData: DialogData) : DialogViewState()
    data object None : DialogViewState()
}
