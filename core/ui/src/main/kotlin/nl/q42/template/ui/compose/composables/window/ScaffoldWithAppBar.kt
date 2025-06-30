package nl.q42.template.ui.compose.composables.window

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import nl.q42.template.core.ui.R
import nl.q42.template.ui.theme.PreviewAll
import nl.q42.template.ui.theme.TemplateTheme


@Composable
fun ScaffoldWithAppBar(
    modifier: Modifier = Modifier,
    title: String?,
    titleDescription: String = title ?: "",
    titleMaxLines: Int = 1,
    titleStyle: TextStyle = TemplateTheme.typography.h1,
    onNavIconClicked: (() -> Unit)?,
    navIconDescription: String = stringResource(id = R.string.action_back),
    navIconPainter: Painter = painterResource(id = R.drawable.arrow_back_24),
    contentScrollState: ScrollableState? = null,
    actions: @Composable() (RowScope.() -> Unit) = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = title ?: "",
                titleStyle = titleStyle,
                titleMaxLines = titleMaxLines,
                onNavIconClicked = onNavIconClicked,
                navIconPainter = navIconPainter,
                navIconDescription = navIconDescription,
                contentScrollState = contentScrollState,
                actions = actions,
                titleContentDescription = titleDescription
            )
        },
        floatingActionButton = floatingActionButton,

        modifier = modifier
            .windowInsetsPadding(
                // other insets are provided by the scaffold (including TopAppBar and navigation bars)
                WindowInsets.ime
            )
            .fillMaxSize()
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
@PreviewAll
private fun ScaffoldWithAppBarPreview() {
    TemplateTheme {
        Box(
            modifier = Modifier.background(TemplateTheme.colors.surface)
        ) {
            ScaffoldWithAppBar(
                title = "Title",
                onNavIconClicked = { },
                content = { paddingValues ->
                    // Content goes here
                }
            )
        }
    }
}