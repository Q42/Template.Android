package nl.q42.template.home.second.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import nl.q42.template.home.second.presentation.HomeSecondViewState
import nl.q42.template.ui.compose.composables.text.H1Text
import nl.q42.template.ui.compose.composables.widgets.TemplateButton
import nl.q42.template.ui.compose.composables.window.ColumnScreenContent
import nl.q42.template.ui.theme.Dimens
import nl.q42.template.ui.theme.PreviewAll
import nl.q42.template.ui.theme.TemplateTheme

@Composable
fun HomeSecondContent(
    viewState: HomeSecondViewState,
    onBackClicked: () -> Unit,
    insetsPadding: PaddingValues,
    contentScrollState: ScrollState,
) {
    ColumnScreenContent(
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        insetsPadding = insetsPadding,
        scrollState = contentScrollState,
    ) {

        H1Text(text = viewState.title)

        Spacer(Modifier.height(Dimens.componentSpacingVertical))

        TemplateButton("Close", onClick = onBackClicked)
    }
}

@Composable
@PreviewAll
private fun HomeSecondContentPreview() {
    TemplateTheme{
        HomeSecondContent(
            viewState = HomeSecondViewState(title = "Home Second Screen"),
            onBackClicked = {},
            insetsPadding = PaddingValues(),
            contentScrollState = ScrollState(0),
        )
    }
}