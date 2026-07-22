package nl.q42.template.navigation.viewmodel

import nl.q42.template.navigation.Destination
import java.util.UUID

sealed class AppNavigationState {
    data object Idle : AppNavigationState()
    data class NavigateToRoute(
        val destination: Destination,
        val backstackBehavior: BackstackBehavior,
        val id: String = UUID.randomUUID().toString()
    ) : AppNavigationState()

    data class PopToDestination(val destination: Destination, val id: String = UUID.randomUUID().toString()) : AppNavigationState()

    data class NavigateUp(val id: String = UUID.randomUUID().toString()) : AppNavigationState()
}

sealed class BackstackBehavior {
    /**
     * Adds the destination to the backstack as usual.
     */
    data object Default : BackstackBehavior()

    /**
     * Removes the current destination from the backstack before navigating.
     *
     * When navigating A -> B -> C. If B -> C is set to RemoveCurrent,
     * the backstack will be A -> C.
     */
    data object RemoveCurrent : BackstackBehavior()

    /**
     * Clears the backstack and sets the target destination as the backstack's root.
     *
     * When navigating A -> B -> C. If B -> C is set to Clear, the backstack will be C.
     */
    data object Clear : BackstackBehavior()
}
