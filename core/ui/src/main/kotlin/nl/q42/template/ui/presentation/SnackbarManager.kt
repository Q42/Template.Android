package nl.q42.template.ui.presentation

import android.view.accessibility.AccessibilityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

private const val DEFAULT_SNACKBAR_DURATION_MILLIS = 4000L

@Singleton
class SnackbarManager @Inject constructor(private val accessibilityManager: AccessibilityManager) : ViewModel() {

    private var showSnackbarJob: Job? = null

    private val _uiState: MutableStateFlow<SnackBarSpec?> = MutableStateFlow(null)
    val uiState: Flow<SnackBarSpec?> = _uiState

    /** No queueing is implemented. When a Snackbar is currently showing, it will be replaced */
    fun showSnackbar(spec: SnackBarSpec) {
        showSnackbarJob?.cancel()
        showSnackbarJob = viewModelScope.launch {
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