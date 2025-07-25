package nl.q42.template.ui.di

import android.app.Application
import android.view.accessibility.AccessibilityManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Singleton
    @Provides
    fun provideAccessibilityManager(application: Application): AccessibilityManager =
        application.getSystemService(
            Application.ACCESSIBILITY_SERVICE
        ) as AccessibilityManager

}
