package nl.q42.data

import nl.q42.data.user.models.User

public interface UserRepository {
    fun getUser(): User
}
