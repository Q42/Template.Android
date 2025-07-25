package nl.q42.template.home.main.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.home.main.presentation.HomeViewModel
import nl.q42.template.ui.compose.OnLifecycleResume
import nl.q42.template.ui.compose.composables.dialog.InitDialogPresenter
import nl.q42.template.ui.compose.composables.window.ScaffoldWithAppBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    OnLifecycleResume(viewModel::onScreenResumed)
    InitDialogPresenter(dialogPresenter = viewModel)

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    ScaffoldWithAppBar(
        title = null, // home screen does not have a title
        onNavIconClicked = null, // home screen does not have a navigation icon
        content = { insetsPadding ->
            HomeContent(
                viewState = viewState,
                insetsPadding = insetsPadding,
                onLoadClicked = viewModel::onLoadClicked,
                onOpenSecondScreenClicked = viewModel::onOpenSecondScreenClicked,
                onOpenOnboardingClicked = viewModel::onOpenOnboardingClicked,
                onShowDummySnackBarClicked = viewModel::onShowDummySnackBarClicked,
                onShowDialogClicked = viewModel::onShowDialogClicked,
            )
        },
    )

}
