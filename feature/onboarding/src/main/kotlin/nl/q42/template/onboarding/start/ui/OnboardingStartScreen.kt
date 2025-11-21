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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.q42.template.onboarding.start.presentation.OnboardingStartViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnboardingStartScreen(
    viewModel: OnboardingStartViewModel = koinViewModel(),
) {

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
