package nl.q42.template.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp

@ReadOnlyComposable
@Composable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        start = calculateStartPadding(layoutDirection) + other.calculateStartPadding(layoutDirection),
        end = calculateEndPadding(layoutDirection) + other.calculateEndPadding(layoutDirection),
        top = calculateTopPadding() + other.calculateTopPadding(),
        bottom = calculateBottomPadding() + other.calculateBottomPadding(),
    )
}

/**
 * Subtracts the padding values of [other] from this [PaddingValues].
 *
 * NOTE: PaddingValues can never be negative, so if subtracting results in a negative value, it will be clamped to zero.
 *
 * @param other The [PaddingValues] to subtract.
 * @return A new [PaddingValues] with the result of the subtraction.
 */
@ReadOnlyComposable
@Composable
operator fun PaddingValues.minus(other: PaddingValues): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return PaddingValues(
        start = (calculateStartPadding(layoutDirection) - other.calculateStartPadding(layoutDirection)).coerceAtLeast(0.dp),
        end = (calculateEndPadding(layoutDirection) - other.calculateEndPadding(layoutDirection)).coerceAtLeast(0.dp),
        top = (calculateTopPadding() - other.calculateTopPadding()).coerceAtLeast(0.dp),
        bottom = (calculateBottomPadding() - other.calculateBottomPadding()).coerceAtLeast(0.dp),
    )
}
