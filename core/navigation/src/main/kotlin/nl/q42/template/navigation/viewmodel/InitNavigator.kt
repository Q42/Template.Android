package nl.q42.template.navigation.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

/**
 * Ensures that [routeNavigator] can navigate on this composition. [routeNavigator] will usually be a ViewModel.
 *
 * More info: https://medium.com/@ffvanderlaan/navigation-in-jetpack-compose-using-viewmodel-state-3b2517c24dde
 */
@Composable
fun InitNavigator(navController: NavHostController, routeNavigator: RouteNavigator) {

    val viewState by routeNavigator.navigationState.collectAsStateWithLifecycle()
    LaunchedEffect(viewState) {
        updateNavigationState(navController, viewState, routeNavigator::onNavigated)
    }
}

/**
 * Navigates to [navigationState].
 */
private fun updateNavigationState(
    navController: NavHostController,
    navigationState: NavigationState,
    onNavigated: (navState: NavigationState) -> Unit,
) {
    when (navigationState) {
        is NavigationState.NavigateToRoute -> {
            when (navigationState.backstackBehavior) {
                BackstackBehavior.Default -> {
                }

                BackstackBehavior.RemoveCurrent -> {
                    navController.popBackStack()
                }

                BackstackBehavior.Clear -> {
                    navController.popBackStack(
                        navController.graph.id,
                        false
                    )
                }
            }
            navController.navigate(navigationState.destination)
            onNavigated(navigationState)
        }

        is NavigationState.PopToDestination -> {
            navController.popBackStack(navigationState.destination, false)
            onNavigated(navigationState)
        }

        is NavigationState.NavigateUp -> {
            navController.navigateUp()
            onNavigated(navigationState)
        }

        is NavigationState.Idle -> {
        }
    }
}