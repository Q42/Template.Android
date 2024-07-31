package nl.q42.template.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.q42.template.actionresult.data.handleAction
import nl.q42.template.domain.user.usecase.GetUserUseCase
import nl.q42.template.feature.home.R
import nl.q42.template.navigation.AppGraphRoutes
import nl.q42.template.navigation.viewmodel.RouteNavigator
import nl.q42.template.ui.home.destinations.HomeSecondScreenDestination
import nl.q42.template.ui.presentation.ViewStateString
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val navigator: RouteNavigator,
) : ViewModel(), RouteNavigator by navigator {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Empty)
    val uiState: StateFlow<HomeViewState> = _uiState.asStateFlow()

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
                onSuccess = { result ->
                    _uiState.update {
                        HomeViewState.Data(
                            ViewStateString.Res(R.string.emailTitle, result.email.value)
                        )
                    }
                },
            )
        }
    }

    fun onOpenSecondScreenClicked() {
        Napier.e(RuntimeException("Open Second Screen tapped. This will be shown as the non-fatal title")) {
            "Open Second Screen Tapped. This will be shown in the Crashlytics breadcrumbs"
        }
        navigateTo(HomeSecondScreenDestination(title = "Hello world!"))
    }

    fun onOpenOnboardingClicked() {
        navigateTo(AppGraphRoutes.onboarding)
    }
}
