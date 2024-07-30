package nl.q42.template.navigation.viewmodel

import java.util.UUID

sealed class NavigationState {
    data object Idle : NavigationState()
    data class NavigateToRoute(
        val route: String,
        val backstackBehavior: BackstackBehavior,
        val id: String = UUID.randomUUID().toString()
    ) : NavigationState()

    data class PopToRoute(val staticRoute: String, val id: String = UUID.randomUUID().toString()) : NavigationState()

    data class NavigateUp(val id: String = UUID.randomUUID().toString()) : NavigationState()
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
