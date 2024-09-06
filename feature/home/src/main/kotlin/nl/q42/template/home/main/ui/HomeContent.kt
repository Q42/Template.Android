package nl.q42.template.home.main.ui

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import nl.q42.template.home.main.presentation.HomeViewState
import nl.q42.template.ui.compose.get
import nl.q42.template.ui.presentation.toViewStateString
import nl.q42.template.ui.theme.PreviewAppTheme
import nl.q42.template.ui.theme.PreviewLightDark

@Composable
internal fun HomeContent(
    viewState: HomeViewState,
    onLoadClicked: () -> Unit,
    onOpenSecondScreenClicked: () -> Unit,
    onOpenOnboardingClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
    ) {

        when(viewState) {
            is HomeViewState.Content -> {
                /**
                 * This is dummy. Use the strings file IRL.
                 */
                Text(text = viewState.userEmailTitle.get())
            }
            HomeViewState.Loading -> CircularProgressIndicator()
            HomeViewState.Error -> Text(text = "Error")
        }

        Button(onClick = onLoadClicked) {
            Text("Refresh")
        }

        Button(onClick = onOpenSecondScreenClicked) {
            Text("Open second screen")
        }
        Button(onClick = onOpenOnboardingClicked) {
            Text("Open onboarding")
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeContentErrorPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState.Error, {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentLoadingPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState.Loading, {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentEmptyPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState.Content(userEmailTitle = "preview@preview.com".toViewStateString()), {}, {}, {})
    }
}
