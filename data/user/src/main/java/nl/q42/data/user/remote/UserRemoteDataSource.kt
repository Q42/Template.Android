package nl.q42.data.user.remote

import nl.q42.data.user.remote.model.UserDTO

class UserRemoteDataSource {

    fun getUser(): UserDTO {
        return UserDTO("frankf@q42.nl") // TODO get from API
    }
}