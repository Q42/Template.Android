package nl.q42.template.domain.user.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.domain.user.model.User

interface UserRepository {
    suspend fun fetchUser(): ActionResult<Unit>
    fun getUserFlow(): Flow<User?>
}
