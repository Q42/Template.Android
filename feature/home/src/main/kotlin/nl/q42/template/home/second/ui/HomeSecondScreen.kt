package nl.q42.template.home.second.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.home.second.presentation.HomeSecondViewModel
import nl.q42.template.ui.compose.composables.text.H1Text
import nl.q42.template.ui.compose.composables.widgets.TemplateButton

@Composable
fun HomeSecondScreen(
    viewModel: HomeSecondViewModel = hiltViewModel(),
) {

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        H1Text(text = viewState.title)

        TemplateButton("Close", onClick = viewModel::onBackClicked)
    }
}
