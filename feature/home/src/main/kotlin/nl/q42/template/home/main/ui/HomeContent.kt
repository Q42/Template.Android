package nl.q42.template.home.main.ui

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import nl.q42.template.home.main.presentation.HomeViewState
import nl.q42.template.ui.compose.composables.text.BodyText
import nl.q42.template.ui.compose.composables.widgets.TemplateButton
import nl.q42.template.ui.compose.get
import nl.q42.template.ui.presentation.toViewStateString
import nl.q42.template.ui.theme.Dimens
import nl.q42.template.ui.theme.PreviewLightDark
import nl.q42.template.ui.theme.PreviewTemplateTheme
import nl.q42.template.ui.theme.TemplateTheme

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
        if (viewState.showError) BodyText("Error", TemplateTheme.colors.error)

        Spacer(Modifier.height(Dimens.componentSpacingVertical))

        Column(
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = spacedBy(Dimens.buttonSpacingVertical)
        ) {
            TemplateButton("Refresh", onClick = onLoadClicked)

            TemplateButton("Open second screen", onClick = onOpenSecondScreenClicked)

            TemplateButton("Open Onboarding", onClick = onOpenOnboardingClicked)

            TemplateButton("Disabled button", enabled = false) {}
        }

    }
}

@PreviewLightDark
@Composable
private fun HomeContentErrorPreview() {
    PreviewTemplateTheme {
        HomeContent(HomeViewState(showError = true), {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentLoadingPreview() {
    PreviewTemplateTheme {
        HomeContent(HomeViewState(isLoading = true), {}, {}, {})
    }
}

@PreviewLightDark
@Composable
private fun HomeContentEmptyPreview() {
    PreviewTemplateTheme {
        HomeContent(HomeViewState(userEmailTitle = "preview@preview.com".toViewStateString()), {}, {}, {})
    }
}
