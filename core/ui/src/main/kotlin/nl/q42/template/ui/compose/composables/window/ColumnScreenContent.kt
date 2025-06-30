package nl.q42.template.ui.compose.composables.window

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.q42.template.ui.compose.plus
import nl.q42.template.ui.theme.Dimens

/**
 * Applies both [insetsPadding] and default screen [contentPadding].
 *
 * For full-width content (such as rows with full-width click areas), use [contentPadding] = PaddingValues(top = Dimens.screenPaddingVertical)
 *
 * */
@Composable
fun ColumnScreenContent(
    modifier: Modifier = Modifier,
    insetsPadding: PaddingValues,
    /** For full-width content (such as rows with full-width click areas), use [contentPadding] = PaddingValues(top = Dimens.screenContentPaddingVertical) */
    contentPadding: PaddingValues = Dimens.screenContentPadding,
    scrollState: ScrollState,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable() (ColumnScope.() -> Unit),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(contentPadding + insetsPadding),
        horizontalAlignment = horizontalAlignment,
    ) {
        content()
    }
}

@Composable
@Preview
private fun ColumnScreenContentPreview() {
    ColumnScreenContent(
        insetsPadding = PaddingValues(),
        scrollState = ScrollState(0),
        contentPadding = PaddingValues(top = Dimens.screenPaddingVertical),
    ) {
        repeat(10) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Red)
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}