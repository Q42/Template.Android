package nl.q42.template.ui.home

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import nl.q42.template.presentation.home.HomeViewState
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

        /**
         * This is dummy. Use the strings file IRL.
         */
        viewState.userEmailTitle?.get()?.let { Text(text = it) }

        if (viewState.isLoading) CircularProgressIndicator()
        if (viewState.showError) Text(text = "Error")

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
        HomeContent(HomeViewState(showError = true), {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentLoadingPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState(isLoading = true), {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentEmptyPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState(userEmailTitle = "preview@preview.com".toViewStateString()), {}, {}, {})
    }
}
