package nl.q42.data.user.local

import nl.q42.data.user.local.model.UserEntity
import javax.inject.Inject

internal class UserLocalDataSource @Inject constructor() {

    fun setUser(userEntity: UserEntity) {
        // store in DB or preferences here: use preferences for simple key-values, for more complex objects, use a Room DB.
    }
}
