package nl.q42.template.presentation.dialog

import androidx.annotation.CallSuper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import nl.q42.template.ui.presentation.dialog.DialogData
import nl.q42.widm.core.presentation.dialog.DialogViewState
import javax.inject.Inject

/**
 * Usage:
 *
 * * ViewModel:
 * ```
 * @inject constructor (
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

    fun showDialog(dialogData: DialogData)

    val dialogUIState: Flow<DialogViewState>
}

internal class DialogPresenterImpl @Inject constructor() : DialogPresenter {

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

    override fun showDialog(dialogData: DialogData) {
        _dialogUIState.update { DialogViewState.ShowDialog(dialogData) }
    }
}
