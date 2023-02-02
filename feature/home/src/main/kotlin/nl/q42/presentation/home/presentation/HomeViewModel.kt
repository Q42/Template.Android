package nl.q42.presentation.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import nl.q42.domain.user.usecase.GetUserUseCase
import nl.q42.template.actionresult.data.handleAction
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    var uiState by mutableStateOf<HomeViewState>(HomeViewState.Empty)
        private set

    fun onScreenResumed() {
        loadUser()
    }

    fun onLoadClicked() {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {

            uiState = HomeViewState.Loading

            handleAction(
                getUserUseCase(),
                onError = { uiState = HomeViewState.Error },
                onSuccess = { uiState = HomeViewState.Data(it.email) },
            )
        }
    }
}
