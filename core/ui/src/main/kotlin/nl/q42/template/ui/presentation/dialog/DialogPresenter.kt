package nl.q42.template.ui.presentation.dialog

import androidx.annotation.CallSuper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Usage:
 *
 * * ViewModel:
 * ```
 * constructor (
 *   private val dialogPresenter: DialogPresenter
 * ) : ViewModel(), DialogPresenter by dialogPresenter {
 *   override fun onDialogConfirmed(tag: Any) {
 *     dialogPresenter.onDialogConfirmed(tag)
 *     // take action based on dialog tag
 *   }
 *
 *   fun onShowDialog(){
 *     dialogPresenter.showDialog(...)
 *   }
 *
 * }
 *
 * Caution: don NOT use `super.onDialogConfirmed(tag)` in ViewModel
 *
 * ```
 *
 * * Screen:
 * ```
 * InitDialogPresenter(dialogPresenter = viewModel)
 * ```
 *
 *
 */
interface DialogPresenter {

    fun onDialogDismissed(tag: Any)

    fun onDialogConfirmed(tag: Any)

    fun showDialog(data: DialogData)

    val dialogUIState: Flow<DialogViewState>
}

internal class DialogPresenterImpl : DialogPresenter {

    private val _dialogUIState = MutableStateFlow<DialogViewState>(DialogViewState.None)
    override val dialogUIState: Flow<DialogViewState> = _dialogUIState

    @CallSuper
    override fun onDialogDismissed(tag: Any) {
        _dialogUIState.update { DialogViewState.None }
    }

    @CallSuper
    override fun onDialogConfirmed(tag: Any) {
        _dialogUIState.update { DialogViewState.None }
    }

    override fun showDialog(data: DialogData) {
        _dialogUIState.update { DialogViewState.ShowDialog(data) }
    }
}
