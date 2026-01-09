package nl.q42.template.navigation.viewmodel

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import nl.q42.template.navigation.Destination

/**
 * Navigator to use when initiating navigation from a ViewModel.
 */
interface RouteNavigator {
    fun onNavigated(state: AppNavigationState)
    fun navigateUp()
    fun popToRoute(destination: Destination)
    fun navigateTo(destination: Destination, backstackBehavior: BackstackBehavior = BackstackBehavior.Default)

    val appNavigationState: StateFlow<AppNavigationState>
}

class MyRouteNavigator : RouteNavigator {

    /**
     * Note that I'm using a single state here, not a list of states. As a result, if you quickly
     * update the state multiple times, the view will only receive and handle the latest state,
     * which is fine for my use case.
     */
    override val appNavigationState: MutableStateFlow<AppNavigationState> =
        MutableStateFlow(AppNavigationState.Idle)

    override fun onNavigated(state: AppNavigationState) {
        // clear navigation state, if state is the current state:
        appNavigationState.compareAndSet(state, AppNavigationState.Idle)
    }

    override fun popToRoute(destination: Destination) = navigate(AppNavigationState.PopToDestination(destination))

    override fun navigateUp() = navigate(AppNavigationState.NavigateUp())

    override fun navigateTo(destination: Destination, backstackBehavior: BackstackBehavior) =
        navigate(AppNavigationState.NavigateToRoute(destination = destination, backstackBehavior = backstackBehavior))

    @VisibleForTesting
    fun navigate(state: AppNavigationState) {
        appNavigationState.value = state
    }
}
