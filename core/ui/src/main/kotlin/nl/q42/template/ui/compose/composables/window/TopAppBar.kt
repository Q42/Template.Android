package nl.q42.template.ui.compose.composables.window

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import io.github.aakira.napier.Napier
import nl.q42.template.core.ui.R
import nl.q42.template.ui.theme.TemplateTheme

@Composable
fun TopAppBar(
    title: String,
    titleStyle: TextStyle = TemplateTheme.typography.h1,
    titleMaxLines: Int = 1,
    onNavIconClicked: (() -> Unit)?,
    navIconPainter: Painter = painterResource(id = R.drawable.arrow_back_24),
    navIconDescription: String = stringResource(id = R.string.action_back),
    contentScrollState: ScrollableState? = null,
    actions: @Composable() (RowScope.() -> Unit) = {},
    titleContentDescription: String = title,
) {
    val textPrimaryColor = TemplateTheme.colors.textPrimary
    val scrolledContainerColor = TemplateTheme.colors.surface.copy(alpha = 0.8f)
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
                    }
//                    .run { // todo necessary?
//                        if (onNavIconClicked == null) this
//                        else { // don't draw under the menu icon:
//                            val leftArrowIconSize = 24.dp
//                            this.padding(horizontal = Dimens.topBarStartPadding + leftArrowIconSize)
//                        }
//                    }
                ,
                maxLines = titleMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            // using a custom implementation instead of scrolledContainerColor, because programmatic
            // nested scrolls (keyboard appear etc.) are not detected: https://issuetracker.google.com/issues/239671493
            // todo: the mentioned issue has "fixed" status. check if fixed and use scrolledContainerColor and remove this custom implementation. Also remove isScrolled()
            containerColor = if (contentScrollState?.isScrolled() == true) {
                scrolledContainerColor
            } else {
                containerColor
            },
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

private fun ScrollableState.isScrolled(): Boolean {
    return when (this) {
        is ScrollState -> {
            return value > 0
        }

        is LazyListState -> {
            return firstVisibleItemScrollOffset > 0
        }

        else -> {
            Napier.e { "Unsupported ScrollableState type: ${this::class.java.simpleName}" }
            false
        }
    }
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
    TemplateTheme {
        TopAppBar(
            "Test",
            onNavIconClicked = {},
            navIconDescription = stringResource(id = R.string.action_back),
            contentScrollState = rememberLazyListState(),
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Share, null)
                }
            },
            titleContentDescription = "Title Description"
        )
    }
}

@Composable
@Preview
private fun TopAppBarLongTitlePreview() {
    TemplateTheme {
        TopAppBar(
            title = "Test with a long title, what does material design do now?",
            onNavIconClicked = {},
            navIconDescription = stringResource(id = R.string.action_back),
            contentScrollState = rememberScrollState(),
            actions = {
                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(Icons.Filled.Share, null)
                }
                IconButton(onClick = {/* Do Something*/ }) {
                    Icon(Icons.Filled.Settings, null)
                }
            },
            titleContentDescription = "titleContentDescription"
        )
    }
}
