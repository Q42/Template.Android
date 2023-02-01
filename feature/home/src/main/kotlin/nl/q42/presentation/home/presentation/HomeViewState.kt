package nl.q42.presentation.home.presentation

sealed class HomeViewState {
    data class Data(val userEmail: String? = null) : HomeViewState()
    object Loading : HomeViewState()
    object Error : HomeViewState()
    object Empty : HomeViewState()
}