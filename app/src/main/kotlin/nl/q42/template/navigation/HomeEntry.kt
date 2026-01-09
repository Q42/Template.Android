package nl.q42.template.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import nl.q42.template.home.main.presentation.HomeViewModel
import nl.q42.template.home.main.ui.HomeScreen
import nl.q42.template.home.second.presentation.HomeSecondViewModel
import nl.q42.template.home.second.ui.HomeSecondScreen
import nl.q42.template.navigation.viewmodel.InitNavigator
import nl.q42.template.navigation.viewmodel.Navigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

internal fun EntryProviderScope<NavKey>.homeEntry(navigator: Navigator) {
    entry<Destination.Home> { key ->
        val viewModel: HomeViewModel = koinViewModel(parameters = { parametersOf(key) })
        InitNavigator(navigator = navigator, routeNavigator = viewModel)
        HomeScreen(viewModel = viewModel)
    }
    entry<Destination.HomeSecond> { key ->
        val viewModel: HomeSecondViewModel = koinViewModel(parameters = { parametersOf(key) })
        InitNavigator(navigator = navigator, routeNavigator = viewModel)
        HomeSecondScreen(viewModel = viewModel)
    }
}
