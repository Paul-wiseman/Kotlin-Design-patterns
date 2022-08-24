package creationalPattern.builderPattern.test

import kotlinx.coroutines.*
import java.io.Closeable
import java.lang.Exception

/*
Your task is to resolve and aggregate user details in an aggregateDataForCurrentUser function.
You have APIs for resolving user details (UserEntity), user comments (List<CommentEntity>)
and friends suggestions (List<FriendEntity>). To resolve current user details, call the resolveCurrentUser function.
To resolve user comments, call fetchUser Comments with id resolved from UserEntity.id.
To resolve user friends suggestions, call fetchSuggested Friends with id resolved from UserEntity.id.

All of these functions are suspend functions; they need to be invoked in some coroutine context.
Implement functions in AggregateUserDataUseCase, where aggregateDataForCurrentUser must satisfy the following requirements:
1. When resolveCurrentUser throws an error, forward the exception.
2. When resolveCurrentUser returns UserEntity, call fetchUser Comments and fetchSuggested Friends
and then aggregate the results into AggregatedData.
3. When fetchUser Comments call takes longer than 2000ms, return an empty comments list.
4. When fetchUser Comments throws an exception, return an empty comments list.
5. When fetchSuggested Friends call takes longer than 2000ms, return an empty friends suggestions list.
6. When fetchSuggestedFriends throws an exception, return an empty friends suggestions list.
And for AggregateUserDataUseCase.close: • When close is invoked, cancel all ongoing coroutine calls.

Hints
• You may create additional coroutineScope inside AggregateUserDataUseCase to handle cancellation
when AggregateUserDataUseCase.close is invoked.

* */
class AggregateUserDataUseCase(
    private val resolveCurrentUser: suspend () -> UserEntity,
    private val fetchUserComments: suspend (UserId) -> List<CommentEntity>,
    private val fetchSuggestedFriends: suspend (UserId) -> List<FriendEntity>
) : Closeable {

    private var userEntity: UserEntity? = null
    private var userComments: List<CommentEntity> = emptyList()
    private var userSuggestedFriends: List<FriendEntity> = emptyList()


    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Exception: $exception")
    }

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)


    private fun getCurrentUserAsync(): Deferred<UserEntity> {
        return scope.async {
            resolveCurrentUser.invoke()
        }
    }


    private fun getUserCommentsAsync(): Deferred<List<CommentEntity>?> {
        return scope.async {
            withTimeoutOrNull(2000L) {
                userEntity?.id?.let { fetchUserComments.invoke(it) }
            } ?: emptyList()
        }
    }

    private fun getCurrentUserJob(): Job {

        return scope.launch {
            try {
                resolveCurrentUser.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getUserCommentsJob(userId: String): Job {
        return scope.launch {
            try {
                val comments = withTimeoutOrNull(2000L) {
                    fetchUserComments.invoke(userId)
                }
                if (comments == null) {
                    userComments = emptyList()
                } else {
                    userComments = comments
                }
            } catch (e: Exception) {
                e.printStackTrace()
                userComments = emptyList()
            }

        }
    }

    private fun getUserFriendsJob(userId: String): Job {
        return scope.launch {
            try {
                val friendEntities = withTimeoutOrNull(2000L) {
                    fetchSuggestedFriends.invoke(userId)
                }
                if (friendEntities == null) {
                    userSuggestedFriends = emptyList()
                } else {
                    userSuggestedFriends = friendEntities
                }

            } catch (e: Exception) {
                e.printStackTrace()
                userSuggestedFriends = emptyList()
            }

        }
    }

    private fun getUserSuggestedFriendsAsync(): Deferred<List<FriendEntity>?> {
        return scope.async {
            withTimeoutOrNull(2000L) {
                userEntity?.id?.let { fetchSuggestedFriends.invoke(it) }
            } ?: emptyList()
        }
    }

    fun aggregateDataForCurrentUser(): AggregatedData {

        scope.launch {
            if (isActive) {
                try {
                    ensureActive()
                    userEntity = getCurrentUserAsync().await()
                    scope.launch {
                        ensureActive()
                        userComments = try {
                            getUserCommentsAsync().await() ?: emptyList()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            emptyList<CommentEntity>()
                            return@launch


                        }
                    }


                    scope.launch {
                        userSuggestedFriends = try {
                            getUserSuggestedFriendsAsync().await() ?: emptyList()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            emptyList<FriendEntity>()
                            return@launch
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    return@launch
                }
            }
        }
        val emptyUser = UserEntity("", "")
        return AggregatedData(userEntity ?: emptyUser, userComments, userSuggestedFriends)
    }

    fun aggregateDataForCurrentUser2(): AggregatedData {
        scope.launch {
            if (isActive) {
                try {
                    getCurrentUserJob().join()
                    userEntity?.id?.let { userId ->
                        getUserCommentsJob(userId).join()
                        getUserFriendsJob(userId).join()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    return@launch
                }
            }

        }
        val emptyUser = UserEntity("", "")
        return AggregatedData(userEntity ?: emptyUser, userComments, userSuggestedFriends)
    }
//    private suspend fun resolveCurrentUser():UserEntity{
//      return try {
//          scope.async { resolveCurrentUser.invoke() }.await()
//      }catch (e:Exception){
//
//      }
//    }

//    suspend fun aggregateDataForCurrentUser(): AggregatedData =
//        withContext(Dispatchers.IO + exceptionHandler) {
//            val currentUser = async(exceptionHandler) { resolveCurrentUser() }
//            val comments = async(exceptionHandler) {
//                withTimeoutOrNull(2000L) {
//                    fetchUserComments(currentUser.await().id)
//                }
//            }
//            val suggestedFriends =
//                async(exceptionHandler) {
//                    withTimeoutOrNull(2000L) {
//                        fetchSuggestedFriends(currentUser.await().id)
//                    }
//                }
//            AggregatedData(
//                currentUser.await(),
//                comments.await() ?: emptyList(),
//                suggestedFriends.await() ?: emptyList()
//            )
//        }

    override fun close() {
        if (scope.isActive) {
            scope.cancel()
        }
    }

    private fun getUserCommentsAsync(userId: String): Deferred<List<CommentEntity>?> {
        return scope.async {
            withTimeoutOrNull(2000) {
                fetchUserComments.invoke(userId)
            }
        }
    }

    private fun getUserSuggestedFriendsAsync(userId: String): Deferred<List<FriendEntity>?> {
        return scope.async {
            withTimeoutOrNull(2000) {
                fetchSuggestedFriends.invoke(userId)
            }
        }
    }


    suspend fun aggregateDataForCurrentUser1(): AggregatedData {

        scope.launch {
            if (isActive) {
                try {
                    userEntity = getCurrentUserAsync().await()

                    userEntity?.id?.let {
                        scope.launch {
                            userComments = try {
                                getUserCommentsAsync(it).await() ?: emptyList()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                emptyList()
                            }
                        }

                        scope.launch {
                            userSuggestedFriends = try {
                                getUserSuggestedFriendsAsync(it).await() ?: emptyList()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                emptyList()
                            }
                        }
                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        val emptyUser = UserEntity("", "")
        return AggregatedData(userEntity ?: emptyUser, userComments, userSuggestedFriends)
    }


//    override fun close() {
//        // cancel all coroutines
//        coroutineContext.cancelChildren()
//    }
}

data class AggregatedData(
    val user: UserEntity,
    val comments: List<CommentEntity>,
    val suggestedFriends: List<FriendEntity>
)

typealias UserId = String

data class UserEntity(val id: UserId, val name: String)

data class CommentEntity(val id: String, val content: String)

data class FriendEntity(val id: String, val name: String)

/**
 *
 * The following is already available on classpath.
 * Please do not uncomment this code or modify.
 * This is only for your convenience to copy-paste code into the IDE


package coroutines
Classes accompanying AggregateUserDataUseCase

package coroutines

data class AggregatedData(

val user: UserEntity,

val comments: List<CommentEntity>, val suggestedFriends: List<FriendEntity>

)

typealias UserId = String

data class UserEntity(val id: UserId, val name: String)

data class CommentEntity(val id: String, val content: String)

data class FriendEntity (val id: String, val name: String)

Available packages/libraries

• 'org.jetbrains.kotlin:kotlin-stdlib:1.4.20' • 'org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.4.2'

 **/

fun sample(x: Any) {
    if (x is List<*>) {
        println(x[0])
    }
    if (x is List<String>) {
        println(x[0].length)
    }
}

var test: String? = "hi there"

fun sample2() {
    if (!test.isNullOrEmpty()) {
        println(test.capitalize())
    }
}

fun sample3() {
    val test: String? by lazy {
        "hi there"
    }
    if (!test.isNullOrEmpty()) {
        println(test.capitalize())
    }
}
fun sample4(){
    val test : MutableList<String> = mutableListOf("hi there")
    (test as MutableList<Int>).add(123)
    println(test)
}

fun sample() {

    val coroutine = CoroutineScope()
    val mainScope = MainScope()
    mainScope.launch {
        yield()
    }
    val test: String? = "hi there"
    if (!test.isNullOrEmpty()){
        println(test.capitalize())
    }
}


fun pointsBelong(x1: Int, y1: Int, x2: Int, y2: Int, x3: Int, y3: Int, xp: Int, yp: Int, xq: Int, yq: Int): Int {
    val ab = Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)).toDouble())
    val bc = Math.sqrt(((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3)).toDouble())
    val ac = Math.sqrt(((x3 - x1) * (x3 - x1) + (y3 - y1) * (y3 - y1)).toDouble())

    if ((ab + bc) <= ac || (bc + ac) <= ab || (ac + ab) <= bc) {
        return 0
    }

    val total = Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0)
    val q1 = Math.abs((x1 * (y2 - yq) + x2 * (yq - y1) + xq * (y1 - y2)) / 2.0)
    val q2 = Math.abs((x1 * (yq - y3) + xq * (y3 - y1) + x3 * (y1 - yq)) / 2.0)
    val q3 = Math.abs((xq * (y2 - y3) + x2 * (y3 - yq) + x3 * (yq - y2)) / 2.0)
    val p1 = Math.abs((x1 * (y2 - yp) + x2 * (yp - y1) + xp * (y1 - y2)) / 2.0)
    val p2 = Math.abs((x1 * (yp - y3) + xp * (y3 - y1) + x3 * (y1 - yp)) / 2.0)
    val p3 = Math.abs((xp * (y2 - y3) + x2 * (y3 - yp) + x3 * (yp - y2)) / 2.0)

    if ((p1 + p2 + p3) == total && (q1 + q2 + q3) != total) {
        return 1
    }

    if ((p1 + p2 + p3) != total && (q1 + q2 + q3) == total) {
        return 2
    }

    if ((p1 + p2 + p3) == total && (q1 + q2 + q3) == total) {
        return 3
    }
    if ((p1 + p2 + p3) != total && (q1 + q2 + q3) != total) {
        return 4
    }
    return 0
}
