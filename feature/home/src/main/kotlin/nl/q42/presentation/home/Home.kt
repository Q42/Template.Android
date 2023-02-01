package nl.q42.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.q42.presentation.home.presentation.HomeViewModel
import nl.q42.presentation.home.presentation.HomeViewState
import nl.q42.template.ui.compose.OnLifecycleResume

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {

    val uiState = viewModel.uiState

    OnLifecycleResume(viewModel::onScreenResumed)

    when (uiState) {
        is HomeViewState.Data -> Text(text = "Email: ${uiState.userEmail}")
        HomeViewState.Empty -> {}
        HomeViewState.Error -> Text(text = "Error")
        HomeViewState.Loading -> Text(text = "Loading")
    }
}
