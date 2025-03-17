package nl.q42.template.domain.main.repo

import kotlinx.coroutines.flow.Flow
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.domain.main.model.User

interface UserRepository {
    suspend fun fetchUser(): ActionResult<Unit>
    fun getUserFlow(): Flow<User?>
}
