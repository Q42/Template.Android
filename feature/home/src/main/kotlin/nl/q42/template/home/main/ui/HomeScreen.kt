package nl.q42.template.home.main.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.home.main.presentation.HomeViewModel
import nl.q42.template.ui.compose.ObserveSnackBarEvents
import nl.q42.template.ui.compose.OnLifecycleResume

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    OnLifecycleResume(viewModel::onScreenResumed)

    val snackBarHostState = remember { SnackbarHostState() }
    ObserveSnackBarEvents(viewModel, snackBarHostState)

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeContent(
        viewState = viewState,
        snackBarHostState = snackBarHostState,
        onLoadClicked = viewModel::onLoadClicked,
        onOpenSecondScreenClicked = viewModel::onOpenSecondScreenClicked,
        onOpenOnboardingClicked = viewModel::onOpenOnboardingClicked,
        onShowDummySnackBarClicked = viewModel::onShowDummySnackBarClicked
    )
}
