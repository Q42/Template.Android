package nl.q42.template.ui.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import nl.q42.template.presentation.home.HomeViewModel
import nl.q42.template.ui.compose.OnLifecycleResume

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    OnLifecycleResume(viewModel::onScreenResumed)

    val viewState = viewModel.uiState
    HomeContent(viewState, viewModel::onLoadClicked)
}