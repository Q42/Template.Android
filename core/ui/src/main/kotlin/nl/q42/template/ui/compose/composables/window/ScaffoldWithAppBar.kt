package nl.q42.template.ui.compose.composables.window

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import nl.q42.template.core.ui.R
import nl.q42.template.ui.theme.AppTheme
import nl.q42.template.ui.theme.PreviewAll


@Composable
fun ScaffoldWithAppBar(
    modifier: Modifier = Modifier,
    title: String?,
    titleDescription: String = title ?: "",
    titleMaxLines: Int = 1,
    titleStyle: TextStyle = AppTheme.typography.h1,
    onNavIconClicked: (() -> Unit)?,
    navIconDescription: String = stringResource(id = R.string.action_back),
    navIconPainter: Painter = painterResource(id = R.drawable.arrow_back_24),
    actions: @Composable() (RowScope.() -> Unit) = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = {
            AppSnackbarHost(LocalSnackbarHostState.current)
        },
        topBar = {
            TopAppBar(
                title = title ?: "",
                titleStyle = titleStyle,
                titleMaxLines = titleMaxLines,
                onNavIconClicked = onNavIconClicked,
                navIconPainter = navIconPainter,
                navIconDescription = navIconDescription,
                scrollBehavior = scrollBehavior,
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
            // listen for content scroll to adjust the TopAppBar appearance
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
@PreviewAll
private fun ScaffoldWithAppBarPreview() {
    AppTheme {
        Box(
            modifier = Modifier.background(AppTheme.colors.surface)
        ) {
            ScaffoldWithAppBar(
                title = "Title",
                onNavIconClicked = { },
                content = { paddingValues ->
                    ColumnScreenContent(
                        insetsPadding = paddingValues,
                        content = {
                            repeat(10) {
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(Color.Red)
                                )
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(Color.Green)
                                )
                            }
                        }
                    )

                },
            )
        }
    }
}