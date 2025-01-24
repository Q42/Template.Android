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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.q42.template.home.second.presentation.HomeSecondViewModel
import nl.q42.template.navigation.viewmodel.InitNavigator
import nl.q42.template.ui.compose.composables.text.H1Text
import nl.q42.template.ui.compose.composables.widgets.TemplateButton

@Destination
@Composable
fun HomeSecondScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeSecondViewModel = hiltViewModel(),
    /**
     * Anything you add here becomes a navigation param that is provided
     * when navigating to this screen. These params are also available in ViewModel,
     * for example this title via savedStateHandle.get("title")
     */
    title: String,
) {

    InitNavigator(navigator, viewModel) // enables viewModel to navigate to other screens

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
