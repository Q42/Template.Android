package nl.q42.template.home.second.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.home.second.presentation.HomeSecondViewModel
import nl.q42.template.ui.compose.composables.window.ScaffoldWithAppBar

@Composable
fun HomeSecondScreen(
    viewModel: HomeSecondViewModel = hiltViewModel(),
) {

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    val contentScrollState = rememberScrollState()

    ScaffoldWithAppBar(
        title = "HomeSecond",
        onNavIconClicked = viewModel::onBackClicked,
    ) { insetsPadding ->
        HomeSecondContent(
            viewState = viewState,
            onBackClicked = viewModel::onBackClicked,
            insetsPadding = insetsPadding,
            contentScrollState = contentScrollState
        )
    }

}

