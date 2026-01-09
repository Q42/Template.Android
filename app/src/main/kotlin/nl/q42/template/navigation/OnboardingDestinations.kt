package nl.q42.template.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import nl.q42.template.navigation.viewmodel.InitNavigator
import nl.q42.template.navigation.viewmodel.Navigator
import nl.q42.template.onboarding.start.presentation.OnboardingStartViewModel
import nl.q42.template.onboarding.start.ui.OnboardingStartScreen
import org.koin.androidx.compose.koinViewModel

internal fun EntryProviderScope<NavKey>.onboardingEntry(navigator: Navigator) {
    entry<Destination.Onboarding> {
        val viewModel: OnboardingStartViewModel = koinViewModel()
        InitNavigator(navigator = navigator, viewModel)

        OnboardingStartScreen(viewModel = viewModel)
    }
}
