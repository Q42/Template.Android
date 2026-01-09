package nl.q42.template.navigation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey

/**
 * Ensures that [routeNavigator] can navigate on this composition. [routeNavigator] will usually be a ViewModel.
 *
 * More info: https://medium.com/@ffvanderlaan/navigation-in-jetpack-compose-using-viewmodel-state-3b2517c24dde
 */
@Composable
fun InitNavigator(navigator: Navigator, routeNavigator: RouteNavigator) {

    val viewState by routeNavigator.appNavigationState.collectAsStateWithLifecycle()
    LaunchedEffect(viewState) {
        updateNavigationState(navigator, viewState, routeNavigator::onNavigated)
    }
}

/**
 * Navigates to [appNavigationState].
 */
private fun updateNavigationState(
    navigator: Navigator,
    appNavigationState: AppNavigationState,
    onNavigated: (navState: AppNavigationState) -> Unit,
) {
    when (appNavigationState) {
        is AppNavigationState.NavigateToRoute -> {
            when (appNavigationState.backstackBehavior) {
                BackstackBehavior.Default -> {
                }

                BackstackBehavior.RemoveCurrent -> {
                    navigator.goBack()
                }

                BackstackBehavior.Clear -> {
                    navigator.clearBackStack()
                }
            }
            navigator.navigate(appNavigationState.destination as NavKey)
            onNavigated(appNavigationState)
        }

        is AppNavigationState.PopToDestination -> {
            navigator.popToRoute(appNavigationState.destination as NavKey)
            onNavigated(appNavigationState)
        }

        is AppNavigationState.NavigateUp -> {
            navigator.goBack()
        }

        is AppNavigationState.Idle -> {
        }
    }
}
