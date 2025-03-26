package nl.q42.template.ui.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset

@Composable
fun FadedVisibility(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(visible = visible, enter = fadeIn(), exit = fadeOut()) {
        content()
    }
}

@Composable
fun SlidedVisibility(visible: Boolean, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, (it.height * 1.5).toInt()) },
        exit = slideOut { IntOffset(0, (it.height * 1.5).toInt()) } + fadeOut(),
    ) {
        content()
    }
}

val enterTransitionNone: EnterTransition = slideInVertically { 0 } // no animation, EnterTransition.None defaults to an animation
val exitTransitionNone: ExitTransition = slideOutVertically { 0 } // no animation, ExitTransition.None defaults to an animation
