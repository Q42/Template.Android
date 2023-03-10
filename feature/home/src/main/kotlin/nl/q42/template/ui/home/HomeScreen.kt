package nl.q42.template.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.q42.template.navigation.viewmodel.InitNavigator
import nl.q42.template.presentation.home.HomeViewModel
import nl.q42.template.presentation.home.HomeViewState
import nl.q42.template.ui.compose.OnLifecycleResume

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    InitNavigator(navigator, viewModel) // enables viewModel to navigate to other screens

    OnLifecycleResume(viewModel::onScreenResumed)

    val viewState by viewModel.uiState.collectAsStateWithLifecycle(initialValue = HomeViewState.Empty)
    HomeContent(viewState, viewModel::onLoadClicked, viewModel::onOpenSecondScreenClicked, viewModel::onOpenOnboardingClicked)
}
