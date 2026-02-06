package nl.q42.template.data.main.remote

import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.q42.template.actionresult.data.mapToActionResult
import nl.q42.template.actionresult.domain.ActionResult
import nl.q42.template.actionresult.domain.map
import nl.q42.template.data.main.local.model.UserEntity
import nl.q42.template.data.main.mapper.mapToEntity
import nl.q42.template.data.main.remote.model.UserDTO

internal class UserRemoteDataSource(
    private val mainApi: MainApi
) {

    suspend fun getUser(): ActionResult<UserEntity> = withContext(Dispatchers.IO) {
        val apiActionResult = mapToActionResult {
            mainApi.getUsers("test@test.com")
        }

        when (apiActionResult) {
            is ActionResult.Success -> {
                apiActionResult.map(UserDTO::mapToEntity)
            }

            is ActionResult.Error -> {
                Logger.e(apiActionResult.throwable) { "getUser failed" }
                apiActionResult
            }
        }
    }
}
