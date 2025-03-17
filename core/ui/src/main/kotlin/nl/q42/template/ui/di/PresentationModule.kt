package nl.q42.template.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import nl.q42.template.ui.presentation.SnackBarPresenter
import nl.q42.template.ui.presentation.SnackBarPresenterImpl

@Module
@InstallIn(ViewModelComponent::class)
class PresentationModule {

    @Provides
    @ViewModelScoped
    fun bindSnackBarPresenter(): SnackBarPresenter = SnackBarPresenterImpl()
}
