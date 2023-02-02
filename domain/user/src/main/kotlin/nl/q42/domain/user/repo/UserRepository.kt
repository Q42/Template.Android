package nl.q42.domain.user.repo

import nl.q42.domain.user.model.User
import nl.q42.template.actionresult.domain.ActionResult

interface UserRepository {
    suspend fun getUser(): ActionResult<User>
}
