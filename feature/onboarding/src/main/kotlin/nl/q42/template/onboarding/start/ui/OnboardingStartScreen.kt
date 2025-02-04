package nl.q42.template.onboarding.start.ui

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
import nl.q42.template.onboarding.start.presentation.OnboardingStartViewModel

@Destination
@Composable
fun OnboardingStartScreen(
    navigator: DestinationsNavigator,
    viewModel: OnboardingStartViewModel = hiltViewModel(),
) {

    InitNavigator(navigator, viewModel) // enables viewModel to navigate to other screens

    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(viewState.title, style = MaterialTheme.typography.titleMedium)

        Button(onClick = viewModel::onBackClicked) {
            Text("Close")
        }
    }
}
