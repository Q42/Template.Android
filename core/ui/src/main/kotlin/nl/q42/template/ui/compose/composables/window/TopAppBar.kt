package nl.q42.template.ui.compose.composables.window

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.q42.template.core.ui.R
import nl.q42.template.ui.theme.AppTheme

@Composable
fun TopAppBar(
    title: String,
    titleStyle: TextStyle = AppTheme.typography.h1,
    titleMaxLines: Int = 1,
    onNavIconClicked: (() -> Unit)?,
    navIconPainter: Painter = painterResource(id = R.drawable.arrow_back_24),
    navIconDescription: String = stringResource(id = R.string.action_back),
    actions: @Composable() (RowScope.() -> Unit) = {},
    titleContentDescription: String = title,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val textPrimaryColor = AppTheme.colors.textPrimary
    val scrolledContainerColor = AppTheme.colors.surface.copy(alpha = 0.8f)
    val containerColor = scrolledContainerColor.copy(alpha = 0.0f)

    CenterAlignedTopAppBar(
        title = {
            if (title.isEmpty()) return@CenterAlignedTopAppBar

            Text(
                text = title,
                color = textPrimaryColor,
                style = titleStyle,
                modifier = Modifier
                    .semantics {
                        contentDescription = titleContentDescription
                        heading()
                    },
                maxLines = titleMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor,
            scrolledContainerColor = scrolledContainerColor
        ),
        actions = {
            CompositionLocalProvider(
                LocalContentColor provides textPrimaryColor
            ) { actions() }
        },
        navigationIcon = {
            if (onNavIconClicked != null) IconButton(
                onClick = onNavIconClicked,
            ) {
                Icon(
                    painter = navIconPainter,
                    tint = textPrimaryColor,
                    contentDescription = navIconDescription,
                )
            }
        }
    )
}

/**
 * Composable to use when there is no TopAppBar but you need the exact top bar spacing.
 */
@Composable
fun TopAppBarPlaceholderSpacing() {
    Spacer(modifier = Modifier.height(64.0.dp))
}

/**
 * Composable to use when there is no TopAppBar but you need the exact top bar spacing.
 */
@Composable
@Preview
fun TopAppBarPlaceholderSpacingPreview() {
    Box(
        modifier = Modifier
            .background(Color(0x44ff0000))
            .width(300.dp)
    ) {
        TopAppBarPlaceholderSpacing()
    }
}

@Composable
@Preview
private fun TopAppBarPreview() {
    AppTheme {
        TopAppBar(
            "Test",
            onNavIconClicked = {},
            actions = {
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.ic_share), null)
                }
            },
            titleContentDescription = "Title Description",
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }
}

@Composable
@Preview
private fun TopAppBarLongTitlePreview() {
    AppTheme {
        TopAppBar(
            title = "Test with a long title, what does material design do now?",
            onNavIconClicked = {},
            actions = {
                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(painter = painterResource(R.drawable.ic_share), null)
                }
                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(painter = painterResource(R.drawable.ic_share), null)
                }
            },
            titleContentDescription = "titleContentDescription",
            scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        )
    }
}
