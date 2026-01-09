package nl.q42.template.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import nl.q42.template.home.main.presentation.HomeViewModel
import nl.q42.template.home.main.ui.HomeScreen
import nl.q42.template.navigation.viewmodel.InitNavigator
import nl.q42.template.navigation.viewmodel.Navigator
import org.koin.androidx.compose.koinViewModel

internal fun EntryProviderScope<NavKey>.homeEntry(navigator: Navigator) {
    entry<Destination.Home> {
        val viewModel: HomeViewModel = koinViewModel()
        InitNavigator(navigator = navigator, routeNavigator = viewModel)

        HomeScreen(viewModel = viewModel)
    }
    entry<Destination.HomeSecond> {
        val viewModel: HomeViewModel = koinViewModel()
        InitNavigator(navigator = navigator, routeNavigator = viewModel)
        HomeScreen(viewModel = viewModel)
    }
}
