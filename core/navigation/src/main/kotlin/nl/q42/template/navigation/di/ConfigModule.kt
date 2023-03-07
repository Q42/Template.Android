package nl.q42.template.navigation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import nl.q42.template.navigation.MyRouteNavigator
import nl.q42.template.navigation.RouteNavigator

@Module
@InstallIn(ViewModelComponent::class)
class NavigationModule {

    @Provides
    @ViewModelScoped
    fun bindRouteNavigator(): RouteNavigator = MyRouteNavigator()
}