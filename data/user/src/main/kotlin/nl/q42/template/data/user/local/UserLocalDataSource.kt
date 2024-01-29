package nl.q42.template.data.user.local

import javax.inject.Inject
import nl.q42.template.data.user.local.model.UserEntity

internal class UserLocalDataSource
@Inject
constructor() {
    fun setUser(userEntity: UserEntity) {
        // store in DB or preferences here: use preferences for simple key-values, for more complex objects, use a Room DB.
    }
}
