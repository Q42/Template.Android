package nl.q42.template.home.main.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.home.main.presentation.HomeViewModel
import nl.q42.template.ui.compose.ObserveSnackBarEvents
import nl.q42.template.ui.compose.OnLifecycleResume
import nl.q42.template.ui.compose.composables.window.ScaffoldWithAppBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    OnLifecycleResume(viewModel::onScreenResumed)

    val snackBarHostState = remember { SnackbarHostState() }
    ObserveSnackBarEvents(viewModel, snackBarHostState)

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    val contentScrollState = rememberScrollState()


    ScaffoldWithAppBar(
        title = null, // home screen does not have a title
        onNavIconClicked = null, // home screen does not have a navigation icon
        content = { insetsPadding ->
            HomeContent(
                viewState = viewState,
                contentScrollState = contentScrollState,
                insetsPadding = insetsPadding,
                onLoadClicked = viewModel::onLoadClicked,
                onOpenSecondScreenClicked = viewModel::onOpenSecondScreenClicked,
                onOpenOnboardingClicked = viewModel::onOpenOnboardingClicked,
                onShowDummySnackBarClicked = viewModel::onShowDummySnackBarClicked
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    )

}
