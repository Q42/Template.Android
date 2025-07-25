package nl.q42.template.ui.presentation.dialog

sealed class DialogViewState {
    data class ShowDialog(val dialogData: DialogData) : DialogViewState()
    data object None : DialogViewState()
}
