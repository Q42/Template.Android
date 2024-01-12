package nl.q42.template.ui.home.second

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.q42.template.navigation.viewmodel.InitNavigator
import nl.q42.template.navigation.viewmodel.RouteNavigator
import nl.q42.template.presentation.home.second.HomeSecondViewModel
import nl.q42.template.presentation.home.second.HomeSecondViewState

@Destination
@Composable
fun HomeSecondScreen(
    navigator: DestinationsNavigator,
    // Anything you add here becomes a navigation param that is provided
    // when navigating to this screen. These params are also available in ViewModel,
    // for example this title via savedStateHandle.get("title")
    title: String,
    modifier: Modifier = Modifier,
    viewModel: HomeSecondViewModel = hiltViewModel()
) {
    InitNavigator(navigator, viewModel as RouteNavigator) // enables viewModel to navigate to other screens

    val viewState by viewModel.uiState.collectAsStateWithLifecycle(
        initialValue = HomeSecondViewState("")
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(viewState.title, style = MaterialTheme.typography.titleMedium)

        Button(onClick = viewModel::onBackClicked) {
            Text("Close")
        }
    }
}
