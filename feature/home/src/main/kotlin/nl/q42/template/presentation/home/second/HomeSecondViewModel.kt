package nl.q42.template.presentation.home.second

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import nl.q42.template.navigation.viewmodel.RouteNavigator
import javax.inject.Inject

@HiltViewModel
class HomeSecondViewModel @Inject constructor(
    private val navigator: RouteNavigator,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), RouteNavigator by navigator {

    private val titleParam = savedStateHandle.get<String>("title") ?: throw IllegalArgumentException("title required")

    private val _uiState = MutableStateFlow<HomeSecondViewState>(HomeSecondViewState(titleParam))
    val uiState: Flow<HomeSecondViewState> = _uiState

    fun onBackClicked() {
        navigateUp()
    }
}
