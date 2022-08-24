package creationalPattern.builderPattern.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.Closeable
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.*

class AggregateUserDataUseCase2(
    private val resolveCurrentUser: suspend () -> UserEntity,
    private val fetchUserComments: suspend (UserId) -> List<CommentEntity>,
    private val fetchSuggestedFriends: suspend (UserId) -> List<FriendEntity>
) : Closeable {

    private var scope: CoroutineScope? = null

    suspend fun aggregateDataForCurrentUser(): AggregatedData {

        val currentUser = resolveCurrentUser.invoke()
        yield()
        val userComments = try {
            withTimeoutOrNull(2000){
                fetchUserComments(currentUser.id)
            } ?: emptyList<CommentEntity>()
        } catch (e: Exception) {
            emptyList<CommentEntity>()
        }
        yield()
        val friendsList = try{
            withTimeoutOrNull(2000) {
                fetchSuggestedFriends(currentUser.id)
            } ?: emptyList<FriendEntity>()
        }catch(e: Exception){
            emptyList<FriendEntity>()
        }

        return AggregatedData(
            currentUser,
            userComments,
            friendsList
        )

    }

    override fun close() {
        scope?.cancel(CancellationException("Closed"))
    }
}

/**
 *
 * The following is already available on classpath.
 * Please do not uncomment this code or modify.
 * This is only for your convenience to copy-paste code into the IDE


package coroutines

data class AggregatedData(
val user: UserEntity,
val comments: List<CommentEntity>,
val suggestedFriends: List<FriendEntity>
)

typealias UserId = String

data class UserEntity(val id: UserId, val name: String)

data class CommentEntity(val id: String, val content: String)

data class FriendEntity(val id: String, val name: String)
 **/