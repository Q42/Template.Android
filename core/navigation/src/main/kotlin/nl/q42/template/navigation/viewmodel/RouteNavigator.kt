package nl.q42.template.navigation.viewmodel

import androidx.annotation.VisibleForTesting
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Navigator to use when initiating navigation from a ViewModel.
 */
interface RouteNavigator {
    fun onNavigated(state: NavigationState)
    fun navigateUp()
    fun popToRoute(staticRoute: String)
    fun navigateTo(route: String, backstackBehavior: BackstackBehavior = BackstackBehavior.Default)
    fun navigateTo(routeDestination: Direction, backstackBehavior: BackstackBehavior = BackstackBehavior.Default)

    val navigationState: StateFlow<NavigationState>
}

class MyRouteNavigator : RouteNavigator {

    /**
     * Note that I'm using a single state here, not a list of states. As a result, if you quickly
     * update the state multiple times, the view will only receive and handle the latest state,
     * which is fine for my use case.
     */
    override val navigationState: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.Idle)

    override fun onNavigated(state: NavigationState) {
        // clear navigation state, if state is the current state:
        navigationState.compareAndSet(state, NavigationState.Idle)
    }

    override fun popToRoute(staticRoute: String) = navigate(NavigationState.PopToRoute(staticRoute))

    override fun navigateUp() = navigate(NavigationState.NavigateUp())

    override fun navigateTo(route: String, backstackBehavior: BackstackBehavior) =
        navigate(NavigationState.NavigateToRoute(route = route, backstackBehavior = backstackBehavior))

    override fun navigateTo(routeDestination: Direction, backstackBehavior: BackstackBehavior) =
        navigateTo(route = routeDestination.route, backstackBehavior = backstackBehavior)

    @VisibleForTesting
    fun navigate(state: NavigationState) {
        navigationState.value = state
    }
}
