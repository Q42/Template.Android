package nl.q42.template.ui.home

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        when (viewState) {
            is HomeViewState.Data -> viewState.userEmailTitle?.let { userEmailTitle ->
                Text(text = userEmailTitle.get())
            }

            HomeViewState.Empty -> {}
            HomeViewState.Error -> Text(text = "Error")
            HomeViewState.Loading -> Text(text = "Loading")
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

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "NOTE: when cloning this template, set up your own Firebase project and replace google-services.json")
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
        HomeContent(HomeViewState.Empty, {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentDataPreview() {
    PreviewAppTheme {
        HomeContent(HomeViewState.Data("preview@preview.com".toViewStateString()), {}, {}, {})
    }
}
