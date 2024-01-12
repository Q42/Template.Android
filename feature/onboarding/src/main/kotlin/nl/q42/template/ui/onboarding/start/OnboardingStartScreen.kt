package nl.q42.template.ui.onboarding.start

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
import nl.q42.template.presentation.onboarding.start.OnboardingStartViewModel
import nl.q42.template.presentation.onboarding.start.OnboardingStartViewState

@Destination
@Composable
fun OnboardingStartScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: OnboardingStartViewModel = hiltViewModel()
) {
    InitNavigator(navigator, viewModel as RouteNavigator) // enables viewModel to navigate to other screens

    val viewState by viewModel.uiState.collectAsStateWithLifecycle(
        initialValue = OnboardingStartViewState("")
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
