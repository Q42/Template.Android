package nl.q42.template.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.q42.template.actionresult.data.handleAction
import nl.q42.template.domain.user.usecase.GetUserUseCase
import nl.q42.template.navigation.viewmodel.RouteNavigator
import nl.q42.template.ui.home.destinations.HomeSecondScreenDestination
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val navigator: RouteNavigator,
) : ViewModel(), RouteNavigator by navigator {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Empty)
    val uiState: Flow<HomeViewState> = _uiState

    init {
        loadUser()
    }

    fun onScreenResumed() {
    }

    fun onLoadClicked() {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {

            _uiState.update { HomeViewState.Loading }

            handleAction(
                getUserUseCase(),
                onError = { _uiState.update { HomeViewState.Error } },
                onSuccess = { result -> _uiState.update { HomeViewState.Data(result.email) } },
            )
        }
    }

    fun onOpenSecondScreenClicked() {
        navigateTo(HomeSecondScreenDestination(title = "Hello world!"))
    }

    fun onOpenOnboardingClicked() {
        // navigateTo()
    }
}
