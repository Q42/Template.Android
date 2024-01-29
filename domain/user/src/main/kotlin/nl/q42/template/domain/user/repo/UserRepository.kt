package nl.q42.template.domain.user.repo

import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.domain.user.model.User

interface UserRepository {
    suspend fun getUser(): ActionResult<User>
}
