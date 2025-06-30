package nl.q42.template.home.second.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import nl.q42.template.home.second.presentation.HomeSecondViewState
import nl.q42.template.ui.compose.composables.text.H1Text
import nl.q42.template.ui.compose.composables.widgets.TemplateButton
import nl.q42.template.ui.compose.composables.window.ColumnScreenContent
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text("A very high box to test scrolling. Tapping the TextField below show show the keyboard and scroll the content up." +
                    "The AppBar should stay visible and the content should not be obscured by the keyboard.")
        }

        TextField(
            value = "test text input",
            onValueChange = { },
            label = { Text("Text Field") },
            modifier = Modifier.height(56.dp)
        )

        TemplateButton("Close", onClick = onBackClicked)
    }
}

@Composable
@PreviewAll
private fun HomeSecondContentPreview() {
    TemplateTheme {
        HomeSecondContent(
            viewState = HomeSecondViewState(title = "Home Second Screen"),
            onBackClicked = {},
            insetsPadding = PaddingValues(),
            contentScrollState = ScrollState(0),
        )
    }
}