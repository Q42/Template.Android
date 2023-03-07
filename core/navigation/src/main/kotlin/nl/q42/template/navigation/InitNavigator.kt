package nl.q42.template.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.aakira.napier.Napier

/**
 * Ensures that [routeNavigator] can navigate on this composition. [routeNavigator] will usually be a ViewModel.
 */
@Composable
fun InitNavigator(destinationsNavigator: DestinationsNavigator, routeNavigator: RouteNavigator) {

    val viewState by routeNavigator.navigationState.collectAsState()

    LaunchedEffect(viewState) {
        Napier.d("Navigating to $viewState")
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
            navigator.navigate(navigationState.routeDestination)
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