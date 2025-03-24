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
import nl.q42.template.ui.theme.PreviewAll
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

        when(viewState) {
            is HomeViewState.Content -> {
                /**
                 * This is dummy. Use the strings file IRL.
                 */
                Text(text = viewState.userEmailTitle.get())
            }
            is HomeViewState.Loading -> CircularProgressIndicator()
            is HomeViewState.Error -> BodyText("Error", TemplateTheme.colors.error)
        }

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

@PreviewAll
@Composable
private fun HomeContentErrorPreview() {
    PreviewTemplateTheme {
        HomeContent(HomeViewState.Error, {}, {}, {})
    }
}

@PreviewAll
@Composable
private fun HomeContentLoadingPreview() {
    PreviewTemplateTheme {
        HomeContent(HomeViewState.Loading, {}, {}, {})
    }
}

@PreviewAll
@Composable
private fun HomeContentEmptyPreview() {
    PreviewTemplateTheme {
        HomeContent(HomeViewState.Content(userEmailTitle = "preview@preview.com".toViewStateString()), {}, {}, {})
    }
}
