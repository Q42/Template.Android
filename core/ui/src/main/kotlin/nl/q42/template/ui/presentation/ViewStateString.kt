package nl.q42.template.ui.presentation

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

/**
 * This class enabled you to use string logic in the ViewModel, especially plurals or replacement parameter logic.
 *
 * All strings will be refreshed on view recreation (i.e. after locale or other config changes).
 *
 * There is one edge case: the formatArgs are not refreshed on config changes, so if you use a string with a
 * replacement parameter that is f.e. a localized date string, move your logic to the view instead of using [ViewStateString].
 */
sealed class ViewStateString {
    data class Res(
        @StringRes val stringRes: Int,
        val formatArgs: List<Any> = listOf()
    ) : ViewStateString() {
        // Allow constructing ViewStateString.Res with varargs instead of passing a list
        @Suppress("unused")
        constructor(stringRes: Int, vararg formatArgs: Any) : this(stringRes, formatArgs.toList())
    }

    data class PluralRes(
        @PluralsRes val pluralRes: Int,
        val count: Int,
        val formatArgs: List<Any> = listOf(count)
    ) : ViewStateString() {
        // Allow constructing ViewStateString.PluralRes with varargs instead of passing a list
        @Suppress("unused")
        constructor(pluralRes: Int, count: Int, vararg formatArgs: Any) : this(
            pluralRes,
            count,
            formatArgs.toList()
        )
    }

    data class Basic(val value: String) : ViewStateString()
}

fun String.toViewStateString(): ViewStateString.Basic = ViewStateString.Basic(this)
