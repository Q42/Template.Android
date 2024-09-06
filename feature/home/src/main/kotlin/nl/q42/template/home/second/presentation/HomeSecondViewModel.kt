package nl.q42.template.home.second.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.q42.template.navigation.viewmodel.RouteNavigator
import javax.inject.Inject

@HiltViewModel
class HomeSecondViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), RouteNavigator by navigator {

    private val titleParam = savedStateHandle.get<String>("title") ?: throw IllegalArgumentException("title required")

    private val _uiState = MutableStateFlow<HomeSecondViewState>(HomeSecondViewState(titleParam))
    val uiState: StateFlow<HomeSecondViewState> = _uiState.asStateFlow()

    fun onBackClicked() {
        navigateUp()
    }
}
