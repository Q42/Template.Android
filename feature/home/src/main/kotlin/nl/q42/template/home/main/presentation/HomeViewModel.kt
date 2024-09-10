package nl.q42.template.home.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.q42.template.actionresult.data.handleAction
import nl.q42.template.domain.user.usecase.FetchUserUseCase
import nl.q42.template.domain.user.usecase.GetUserFlowUseCase
import nl.q42.template.feature.home.R
import nl.q42.template.home.destinations.HomeSecondScreenDestination
import nl.q42.template.navigation.AppGraphRoutes
import nl.q42.template.navigation.viewmodel.RouteNavigator
import nl.q42.template.ui.presentation.ViewStateString
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchUserUseCase: FetchUserUseCase,
    private val getUserFlowUseCase: GetUserFlowUseCase,
    private val navigator: RouteNavigator,
) : ViewModel(), RouteNavigator by navigator {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState())
    val uiState: StateFlow<HomeViewState> = _uiState.asStateFlow()

    init {
        startObservingUserChanges()
        fetchUser()
        Napier.e("Nino test error")
    }

    fun onScreenResumed() {
    }

    fun onLoadClicked() {
        fetchUser()
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

    fun fetchUser() {
        viewModelScope.launch {

            _uiState.update { it.copy(showError = false, isLoading = true) }

            handleAction(
                action = fetchUserUseCase(),
                onError = { _uiState.update { it.copy(showError = true, isLoading = false) } },
                onSuccess = { _uiState.update { it.copy(isLoading = false) } },
            )
        }
    }

    private fun startObservingUserChanges() {
        getUserFlowUseCase().filterNotNull().onEach { user ->
            _uiState.update {
                it.copy(
                    userEmailTitle = ViewStateString.Res(R.string.emailTitle, user.email.value)
                )
            }
        }.launchIn(viewModelScope)
    }
}
