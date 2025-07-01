package nl.q42.template.ui.compose.composables.window

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState provided") }
