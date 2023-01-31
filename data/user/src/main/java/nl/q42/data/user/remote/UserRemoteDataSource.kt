package nl.q42.data.user.remote

import nl.q42.data.user.local.model.UserEntity
import nl.q42.data.user.remote.model.UserDTO
import nl.q42.data.user.remote.model.mapToEntity

class UserRemoteDataSource {

    fun getUser(): UserEntity {
        return UserDTO("frankf@q42.nl").mapToEntity() // TODO get from API
    }
}
