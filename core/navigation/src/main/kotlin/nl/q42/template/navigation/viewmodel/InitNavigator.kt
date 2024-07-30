package nl.q42.template.navigation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.q42.template.navigation.AppGraphRoutes

/**
 * Ensures that [routeNavigator] can navigate on this composition. [routeNavigator] will usually be a ViewModel.
 *
 * More info: https://medium.com/@ffvanderlaan/navigation-in-jetpack-compose-using-viewmodel-state-3b2517c24dde
 */
@Composable
fun InitNavigator(destinationsNavigator: DestinationsNavigator, routeNavigator: RouteNavigator) {

    val viewState by routeNavigator.navigationState.collectAsStateWithLifecycle()
    LaunchedEffect(viewState) {
        updateNavigationState(destinationsNavigator, viewState, routeNavigator::onNavigated)
    }
}

/**
 * Navigates to [navigationState].
 */
private fun updateNavigationState(
    navigator: DestinationsNavigator,
    navigationState: NavigationState,
    onNavigated: (navState: NavigationState) -> Unit,
) {
    when (navigationState) {
        is NavigationState.NavigateToRoute -> {
            when (navigationState.backstackBehavior) {
                BackstackBehavior.Default -> {
                }

                BackstackBehavior.RemoveCurrent -> {
                    navigator.popBackStack()
                }

                BackstackBehavior.Clear -> {
                    navigator.popBackStack(AppGraphRoutes.root, true)
                }
            }
            navigator.navigate(navigationState.route)
            onNavigated(navigationState)
        }

        is NavigationState.PopToRoute -> {
            navigator.popBackStack(navigationState.staticRoute, false)
            onNavigated(navigationState)
        }

        is NavigationState.NavigateUp -> {
            navigator.navigateUp()
            onNavigated(navigationState)
        }

        is NavigationState.Idle -> {
        }
    }
}
