package nl.q42.template.onboarding.presentation.start

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.q42.template.navigation.viewmodel.RouteNavigator
import javax.inject.Inject

@HiltViewModel
class OnboardingStartViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), RouteNavigator by navigator {

    private val _uiState = MutableStateFlow(OnboardingStartViewState("start"))
    val uiState: StateFlow<OnboardingStartViewState> = _uiState.asStateFlow()

    fun onBackClicked() {
        navigateUp()
    }
}
