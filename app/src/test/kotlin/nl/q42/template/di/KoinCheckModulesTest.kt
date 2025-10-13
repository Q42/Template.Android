package nl.q42.template.di

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class KoinCheckModulesTest : KoinTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun `Check all Koin modules and their dependencies`() {
        appModule.verify(
            extraTypes = listOf(
                // Primitive types used in value classes
                String::class,
                Boolean::class,
                Int::class,
                // Android framework types injected at runtime
                SavedStateHandle::class,
                CoroutineScope::class,
            )
        )
    }
}