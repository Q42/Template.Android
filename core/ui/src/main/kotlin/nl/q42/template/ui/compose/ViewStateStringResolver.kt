@file:OptIn(ExperimentalComposeUiApi::class)

package nl.q42.template.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import nl.q42.template.ui.presentation.ViewStateString

/**
 * Resolve a ViewStateString to a string from a Compose context.
 */
@Composable
fun ViewStateString.resolve(): String {
    return when (this) {
        is ViewStateString.Res -> {
            // Map any nested ViewStateStrings to their resolved values.
            val resolvedArguments =
                this.formatArgs.map { if (it is ViewStateString) it.resolve() else it }
                    .toTypedArray()
            stringResource(id = this.stringRes, formatArgs = resolvedArguments)
        }

        is ViewStateString.PluralRes -> {
            pluralStringResource(id = pluralRes, count = count, count)
        }

        is ViewStateString.Basic -> this.value
    }
}
