package nl.q42.template.onboarding.start.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.q42.template.navigation.viewmodel.RouteNavigator

class OnboardingStartViewModel(
    private val navigator: RouteNavigator,
) : ViewModel(), RouteNavigator by navigator {

    private val _uiState = MutableStateFlow(OnboardingStartViewState("Onboarding start"))
    val uiState: StateFlow<OnboardingStartViewState> = _uiState.asStateFlow()

    fun onBackClicked() {
        navigateUp()
    }
}
