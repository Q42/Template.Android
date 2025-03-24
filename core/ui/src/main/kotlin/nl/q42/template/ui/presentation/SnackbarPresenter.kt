package nl.q42.template.ui.presentation

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Presents SnackBars using One-Time-UI-Events.
 *
 * More info  on One-Time-UI-Events: https://github.com/leonard-palm/compose-state-events
 */
interface SnackBarPresenter {

    val snackBarEvents: StateFlow<StateEventWithContent<SnackBarMessageViewState>>

    fun onSnackBarMessageShown()
    fun showSnackBarMessage(state: SnackBarMessageViewState)
}

data class SnackBarMessageViewState(val title: ViewStateString)

class SnackBarPresenterImpl : SnackBarPresenter {

    override val snackBarEvents: MutableStateFlow<StateEventWithContent<SnackBarMessageViewState>> = MutableStateFlow(consumed())

    override fun onSnackBarMessageShown() {
        snackBarEvents.update { consumed() }
    }

    override fun showSnackBarMessage(state: SnackBarMessageViewState) {
        snackBarEvents.update { triggered(state) }
    }
}
