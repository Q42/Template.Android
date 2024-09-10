package nl.q42.template.data.user.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import nl.q42.template.data.user.local.model.UserEntity
import javax.inject.Inject

internal class UserLocalDataSource @Inject constructor() {

    private val userFlow = MutableStateFlow<UserEntity?>(null) // this is dummy code, replace it with your own local storage implementation.

    fun setUser(userEntity: UserEntity) {

        // usually you store in DataStore or DB here...

        userFlow.update { userEntity } // this is dummy code, replace it with your own local storage implementation.
    }

    fun getUserFlow(): Flow<UserEntity?> = userFlow
}
