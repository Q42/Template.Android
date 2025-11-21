package nl.q42.template.navigation.di

import nl.q42.template.navigation.viewmodel.MyRouteNavigator
import nl.q42.template.navigation.viewmodel.RouteNavigator
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val navigationModule = module {
    factoryOf(::MyRouteNavigator) { bind<RouteNavigator>() }
}