package nl.q42.template.home.main.ui

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
    snackBarHostState: SnackbarHostState,
    onLoadClicked: () -> Unit,
    onOpenSecondScreenClicked: () -> Unit,
    onOpenOnboardingClicked: () -> Unit,
    onShowDummySnackBarClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        snackbarHost = { // display SnackBars here, because it might differ per screen where exactly you want to display it / if they are supported.
            SnackbarHost(snackBarHostState) { data -> Snackbar(snackbarData = data) }
        },
    ) { paddingValues ->

        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
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
            Button(onClick = onShowDummySnackBarClicked) {
                Text("Show dummy SnackBar")
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun HomeContentErrorPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState(showError = true), SnackbarHostState(), {}, {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentLoadingPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState(isLoading = true), SnackbarHostState(), {}, {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentEmptyPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState(userEmailTitle = "preview@preview.com".toViewStateString()), SnackbarHostState(), {}, {}, {}, {})
    }
}
