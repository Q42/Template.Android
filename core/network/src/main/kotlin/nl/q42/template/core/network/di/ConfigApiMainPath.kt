package nl.q42.template.core.network.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigApiMainPath

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigLogHttpCalls