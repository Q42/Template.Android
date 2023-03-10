package nl.q42.template.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavGraphSpec
import nl.q42.template.navigation.transitions.defaultEnterTransition
import nl.q42.template.navigation.transitions.defaultExitTransition
import nl.q42.template.navigation.transitions.defaultPopEnterTransition
import nl.q42.template.navigation.transitions.defaultPopExitTransition

@OptIn(ExperimentalMaterialNavigationApi::class)
@ExperimentalAnimationApi
@Composable
fun AppNavigation(
    navController: NavHostController,
    navGraph: NavGraphSpec,
    modifier: Modifier = Modifier,
) {
    DestinationsNavHost(
        engine = rememberAnimatedNavHostEngine(
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                enterTransition = { defaultEnterTransition(initialState, targetState) },
                exitTransition = { defaultExitTransition(initialState, targetState) },
                popEnterTransition = { defaultPopEnterTransition() },
                popExitTransition = { defaultPopExitTransition() },
            )
        ),
        navController = navController,
        navGraph = navGraph,
        modifier = modifier,
    )
}