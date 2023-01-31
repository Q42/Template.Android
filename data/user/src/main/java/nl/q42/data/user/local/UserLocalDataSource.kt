package nl.q42.data.user.local

import nl.q42.data.user.local.model.UserEntity
import javax.inject.Inject

internal class UserLocalDataSource @Inject constructor() {

    fun setUser(userEntity: UserEntity) {
        // TODO store in DB
    }
}
