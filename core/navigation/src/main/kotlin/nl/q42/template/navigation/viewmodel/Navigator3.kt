package nl.q42.template.navigation.viewmodel

import androidx.navigation3.runtime.NavKey
import io.github.aakira.napier.Napier

/**
 * Handles navigation events (forward and back) by updating the navigation state.
 */
class Navigator(val state: NavigationState){
    fun navigate(route: NavKey){
        if (route in state.backStacks.keys){
            // This is a top level route, just switch to it.
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun popToRoute(route: NavKey){
        val currentStack = state.backStacks[state.topLevelRoute]
        if (currentStack != null) {
            val destinationIndex = currentStack.lastIndexOf(route)
            if (destinationIndex != -1) {
                val elementsToRemove = currentStack.size - 1 - destinationIndex
                repeat(elementsToRemove) {
                    currentStack.removeLastOrNull()
                }
            } else {
                Napier.e { "Route $route not found in the current stack" }
            }
        }
    }

    fun clearBackStack(){
        state.backStacks[state.topLevelRoute]?.clear()
        // todo keep top level route?
    }


    fun goBack(){
        val currentStack = state.backStacks[state.topLevelRoute] ?: run {
            Napier.e { "Stack for ${state.topLevelRoute} not found" }
            null
        }

        if (currentStack == null) return

        val currentRoute = currentStack.last()

        // If we're at the base of the current route, go back to the start route stack.
        if (currentRoute == state.topLevelRoute){
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }
}
