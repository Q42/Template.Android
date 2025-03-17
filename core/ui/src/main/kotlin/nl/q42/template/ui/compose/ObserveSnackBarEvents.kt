package nl.q42.template.ui.compose

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.palm.composestateevents.EventEffect
import nl.q42.template.ui.presentation.SnackBarPresenter
import nl.q42.template.ui.presentation.SnackBarMessageViewState

/**
 * Re-usable composable that connects a SnackBarPresenter event's to the UI state.
 */
@Composable
fun ObserveSnackBarEvents(snackBarPresenter: SnackBarPresenter, snackBarHostState: SnackbarHostState) {
    val context = LocalContext.current
    val snackBarEvent by snackBarPresenter
        .snackBarEvents
        .collectAsStateWithLifecycle()

    EventEffect(
        event = snackBarEvent,
        onConsumed = snackBarPresenter::onSnackBarMessageShown
    ) { state: SnackBarMessageViewState ->
        snackBarHostState.showSnackbar(
            message = state.title.get(context),
            duration = SnackbarDuration.Short
        )
    }
}
