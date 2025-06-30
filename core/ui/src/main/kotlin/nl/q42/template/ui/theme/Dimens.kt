package nl.q42.template.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Dimens {
    val componentSpacingVertical = 24.dp
    /** Spacing between 2 vertically stacked buttons */
    val buttonSpacingVertical = 8.dp

    val screenPaddingVertical = 32.dp
    val screenPaddingHorizontal: Dp = 24.dp
    val screenContentPadding =
        PaddingValues(
            horizontal = screenPaddingHorizontal,
            vertical = screenPaddingVertical
        )
}
