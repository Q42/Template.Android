package nl.q42.template.home.main.ui

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import nl.q42.template.home.main.presentation.HomeViewState
import nl.q42.template.ui.compose.composables.text.BodyText
import nl.q42.template.ui.compose.composables.widgets.AppButton
import nl.q42.template.ui.compose.composables.window.ColumnScreenContent
import nl.q42.template.ui.compose.get
import nl.q42.template.ui.presentation.toViewStateString
import nl.q42.template.ui.theme.AppTheme
import nl.q42.template.ui.theme.Dimens
import nl.q42.template.ui.theme.PreviewAll
import nl.q42.template.ui.theme.PreviewAppTheme

@Composable
internal fun HomeContent(
    viewState: HomeViewState,
    insetsPadding: PaddingValues,
    onLoadClicked: () -> Unit,
    onOpenSecondScreenClicked: () -> Unit,
    onOpenOnboardingClicked: () -> Unit,
    onShowDummySnackBarClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onShowDialogClicked: () -> Unit
) {

    ColumnScreenContent(
        modifier = modifier,
        insetsPadding = insetsPadding,
        horizontalAlignment = CenterHorizontally,
        content = {

            when (viewState) {
                is HomeViewState.Content -> {
                    /**
                     * This is dummy. Use the strings file IRL.
                     */
                    Text(text = viewState.userEmailTitle.get())
                }

                is HomeViewState.Loading -> CircularProgressIndicator()
                is HomeViewState.Error -> BodyText("Error", AppTheme.colors.error)
            }

            Spacer(Modifier.height(Dimens.componentSpacingVertical))

            Column(
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = spacedBy(Dimens.buttonSpacingVertical)
            ) {
                AppButton("Refresh", onClick = onLoadClicked)

                AppButton("Open second screen", onClick = onOpenSecondScreenClicked)

                AppButton("Open Onboarding", onClick = onOpenOnboardingClicked)

                AppButton("Disabled button", enabled = false) {}

                AppButton("Show dummy SnackBar", onClick = onShowDummySnackBarClicked)

                TemplateButton("Show Dialog for userid 1337", onClick = onShowDialogClicked)
            }

        }
    )
}

@PreviewAll
@Composable
private fun HomeContentErrorPreview() {
    PreviewAppTheme {
        HomeContent(
            viewState = HomeViewState.Error,
            insetsPadding = PaddingValues(),
            onLoadClicked = {},
            onOpenSecondScreenClicked = {},
            onOpenOnboardingClicked = {},
            onShowDummySnackBarClicked = {},
            onShowDialogClicked = { },
        )
    }
}

@PreviewAll
@Composable
private fun HomeContentLoadingPreview() {
    PreviewAppTheme {
        HomeContent(
            HomeViewState.Loading,
            insetsPadding = PaddingValues(),
            onLoadClicked = {},
            onOpenSecondScreenClicked = {},
            onOpenOnboardingClicked = {},
            onShowDummySnackBarClicked = {},
            onShowDialogClicked = { },
        )
    }
}

@PreviewAll
@Composable
private fun HomeContentEmptyPreview() {
    PreviewAppTheme {
        HomeContent(
            HomeViewState.Content(userEmailTitle = "preview@preview.com".toViewStateString()),
            insetsPadding = PaddingValues(),
            onLoadClicked = {},
            onOpenSecondScreenClicked = {},
            onOpenOnboardingClicked = {},
            onShowDummySnackBarClicked = {},
            onShowDialogClicked = { }
        )
    }
}
