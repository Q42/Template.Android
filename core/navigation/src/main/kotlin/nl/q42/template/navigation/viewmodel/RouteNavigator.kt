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
    fun navigateTo(routeDestination: Direction)

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

    override fun navigateTo(routeDestination: Direction) = navigate(NavigationState.NavigateToRoute(routeDestination))

    @VisibleForTesting
    fun navigate(state: NavigationState) {
        navigationState.value = state
    }
}