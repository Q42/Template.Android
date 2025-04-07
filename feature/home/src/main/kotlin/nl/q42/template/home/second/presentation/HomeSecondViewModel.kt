package nl.q42.template.home.second.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.q42.template.navigation.Destination
import nl.q42.template.navigation.viewmodel.RouteNavigator
import javax.inject.Inject

@HiltViewModel
class HomeSecondViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), RouteNavigator by navigator {

    private val params = savedStateHandle.toRoute<Destination.HomeSecond>()

    private val _uiState = MutableStateFlow(HomeSecondViewState(params.title))
    val uiState: StateFlow<HomeSecondViewState> = _uiState.asStateFlow()

    fun onBackClicked() {
        navigateUp()
    }
}
