package nl.q42.template.di

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

class KoinCheckModulesTest : KoinTest {

    /**
     * Note: this checks that all the dependencies in the DI configuration work well among them,
     * but it doesn't prevent crashes at runtime if the consumer code of the DI dependencies misses
     * a required parameter. This will happen for example with a ViewModel(val myID: String) where
     * the caller of koinViewModel() forgets to pass the myID parameter or the parameter's type doesn't match.
     */
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