package nl.q42.template.data.main.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import nl.q42.template.data.main.local.model.UserEntity

internal class UserLocalDataSource() {

    private val userFlow =
        MutableSharedFlow<UserEntity?>() // this is dummy code, replace it with your own local storage implementation.

    suspend fun setUser(userEntity: UserEntity) {

        // usually you store in DataStore or DB here...

        userFlow.emit(userEntity) // this is dummy code, replace it with your own local storage implementation.
    }

    fun getUserFlow(): Flow<UserEntity?> = userFlow
}
