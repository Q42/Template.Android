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
import nl.q42.template.ui.compose.composables.widgets.TemplateButton
import nl.q42.template.ui.compose.get
import nl.q42.template.ui.presentation.toViewStateString
import nl.q42.template.ui.theme.AppTheme
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
        if (viewState.showError) Text(
            text = "Error",
            style = AppTheme.typography.body,
            color = AppTheme.colors.error
        )

        TemplateButton("Refresh", onLoadClicked)

        TemplateButton("Open second screen", onOpenSecondScreenClicked)

//        Button(onClick = onOpenOnboardingClicked) {
//            Text("Open onboarding")
//        }
        TemplateButton("Open Onboarding", onOpenOnboardingClicked)
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
