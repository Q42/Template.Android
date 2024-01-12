package nl.q42.template.presentation.onboarding.start

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import nl.q42.template.navigation.viewmodel.RouteNavigator

@HiltViewModel
class OnboardingStartViewModel
@Inject
constructor(
    private val navigator: RouteNavigator,
    savedStateHandle: SavedStateHandle
) : ViewModel(), RouteNavigator by navigator {
    private val _uiState = MutableStateFlow(OnboardingStartViewState("start"))
    val uiState: Flow<OnboardingStartViewState> = _uiState

    fun onBackClicked() {
        navigateUp()
    }
}
