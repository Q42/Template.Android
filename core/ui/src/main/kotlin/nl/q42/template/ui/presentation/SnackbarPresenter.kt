package nl.q42.template.ui.presentation

import android.view.accessibility.AccessibilityManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import nl.q42.template.ui.compose.MainCoroutineScope
import java.util.UUID

private const val DEFAULT_SNACKBAR_DURATION_MILLIS = 4000L

class SnackbarPresenter(
    private val accessibilityManager: AccessibilityManager,
    private val mainCoroutineScope: MainCoroutineScope,
) {

    private var showSnackbarJob: Job? = null

    private val _uiState: MutableStateFlow<SnackBarSpec?> = MutableStateFlow(null)
    val uiState: Flow<SnackBarSpec?> = _uiState

    /** No queueing is implemented. When a Snackbar is currently showing, it will be replaced */
    fun showSnackbar(spec: SnackBarSpec) {
        showSnackbarJob?.cancel()
        showSnackbarJob = mainCoroutineScope.value.launch {
            _uiState.value = spec
            delay(snackbarDuration(spec))
            _uiState.value = null
        }
    }

    /** No queueing is implemented. When a Snackbar is currently showing, it will be replaced */
    fun showSnackbar(message: ViewStateString, isError: Boolean = false) {
        showSnackbar(SnackBarSpec(message, isError = isError))
    }

    private fun snackbarDuration(spec: SnackBarSpec): Long {
        var contentFlags = AccessibilityManager.FLAG_CONTENT_TEXT
        if (spec.actionLabel != null) {
            contentFlags = contentFlags or AccessibilityManager.FLAG_CONTENT_CONTROLS
        }

        return accessibilityManager.getRecommendedTimeoutMillis(
            DEFAULT_SNACKBAR_DURATION_MILLIS.toInt(),
            contentFlags
        ).toLong()
    }
}

data class SnackBarSpec(
    val message: ViewStateString,
    val actionLabel: String? = null,
    val withDismissAction: Boolean = false,
    val isError: Boolean,
    val id: String = UUID.randomUUID()
        .toString() // used to make every Snackbar unique so we can send the same message twice
)