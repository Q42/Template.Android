package nl.q42.template.core.utils.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigApiMainPath

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigLogHttpCalls

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigAppScheme

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigAppVersionName

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigAppVersionCode