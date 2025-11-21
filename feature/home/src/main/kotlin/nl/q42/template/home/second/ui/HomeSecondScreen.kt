package nl.q42.template.home.second.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.home.second.presentation.HomeSecondViewModel
import nl.q42.template.ui.compose.composables.window.ScaffoldWithAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeSecondScreen(
    viewModel: HomeSecondViewModel = koinViewModel(),
) {

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    ScaffoldWithAppBar(
        title = "HomeSecond",
        onNavIconClicked = viewModel::onBackClicked,
    ) { insetsPadding ->
        HomeSecondContent(
            viewState = viewState,
            onBackClicked = viewModel::onBackClicked,
            insetsPadding = insetsPadding,
        )
    }

}

