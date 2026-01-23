package nl.q42.template.home.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nl.q42.template.actionresult.data.handleAction
import nl.q42.template.domain.main.usecase.FetchUserUseCase
import nl.q42.template.domain.main.usecase.GetUserFlowUseCase
import nl.q42.template.feature.home.R
import nl.q42.template.navigation.Destination
import nl.q42.template.navigation.viewmodel.RouteNavigator
import nl.q42.template.ui.presentation.SnackbarPresenter
import nl.q42.template.ui.presentation.ViewStateString
import nl.q42.template.ui.presentation.dialog.DialogData
import nl.q42.template.ui.presentation.dialog.DialogPresenter
import kotlin.random.Random

class HomeViewModel(
    private val fetchUserUseCase: FetchUserUseCase,
    private val getUserFlowUseCase: GetUserFlowUseCase,
    private val navigator: RouteNavigator,
    private val snackbarPresenter: SnackbarPresenter,
    private val dialogPresenter: DialogPresenter
) : ViewModel(), RouteNavigator by navigator, DialogPresenter by dialogPresenter {

    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Loading)
    val uiState: StateFlow<HomeViewState> = _uiState.asStateFlow()

    init {
        startObservingUserChanges()
        fetchUser()
    }

    override fun onDialogConfirmed(tag: Any) {
        dialogPresenter.onDialogConfirmed(tag)
        // take action based on dialog tag
        snackbarPresenter.showSnackbar(message = ViewStateString.Basic("Dialog confirmed with tag: $tag"))
    }

    fun onScreenResumed() {
    }

    fun onLoadClicked() {
        Logger.i { "onLoadClicked" }
        fetchUser()
    }

    fun onOpenSecondScreenClicked() {
        Napier.e { "Open Second Screen tapped. This will be shown In LogCat and on prod builds also as as the title of a Non-Fatal event" }
        navigateTo(Destination.HomeSecond(title = "Hello world!"))
    }

    fun onOpenOnboardingClicked() {
        navigateTo(Destination.Onboarding)
    }

    fun onShowDummySnackBarClicked() {
        snackbarPresenter.showSnackbar(
            message = ViewStateString.Basic("A SnackBar message. Random: " + Random.nextInt() % 100),
            isError = false
        )
    }

    fun onShowDialogClicked() {
        dialogPresenter.showDialog(
            data = DialogData(
                title = ViewStateString.Basic("Dialog Title"),
                description = ViewStateString.Basic("This is a dialog message. It can be used to show more information or ask for confirmation."),
                tag = "userId 1337",
            )
        )
    }

    private fun fetchUser() {
        viewModelScope.launch {

            _uiState.value = HomeViewState.Loading

            handleAction(
                action = fetchUserUseCase(),
                onError = { _uiState.value = HomeViewState.Error },
                onSuccess = {},
            )
        }
    }

    private fun startObservingUserChanges() {
        getUserFlowUseCase().filterNotNull().onEach { user ->
            _uiState.value = HomeViewState.Content(
                userEmailTitle = ViewStateString.Res(R.string.emailTitle, user.email.value)
            )
        }.launchIn(viewModelScope)
    }
}
