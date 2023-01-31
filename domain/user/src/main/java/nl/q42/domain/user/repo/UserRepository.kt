package nl.q42.domain.user.repo

import nl.q42.domain.user.model.User

interface UserRepository {
    fun getUser(): User
}
