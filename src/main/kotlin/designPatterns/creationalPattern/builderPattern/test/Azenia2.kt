package designPatterns.creationalPattern.builderPattern.test

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.*
import java.io.Closeable

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

//fun main(){
//    // variable definition
//    val jsonTypeAsString = """{"name":"example"}"""
//
//   println( jsonTypeAsString.toObject<MyJsonType>())
//
//}

// type definition
data class MyJsonType(val name: String)


// call to String::toKotlinObject

inline fun <reified T> myGenericFun(){
    if (String is T){

    }
}

fun <T:Any> String.toKotlinObject(c: Class<T>): Class<T> {
    val mapper = jacksonObjectMapper()
    return mapper.readValue(this, c::class.java)
}

inline fun <reified T> String.toObject():T{
    val mapper = jacksonObjectMapper()
    return mapper.readValue(this, T::class.java)
}

fun main(){
//    println(searchingChallenge("No word"))

    val doSomeWork: DoSomeWork? = null
    doSomeWork?.transformName()

}

fun exception(){

}


class DoSomeWork(private val name: String){
    fun transformName(){
        for (i in 0..name.length){
            println(name[i])
        }
    }
}
fun searchingChallenge(string: String):String{
    var highestElement = -1
    var elementIndex = 0
    val separatedWors = string.split("\\s+".toRegex())

  separatedWors.map {
        it.lowercase().groupingBy { it }.eachCount()
            .count{ it.value > 1}
    }.forEachIndexed { index, i ->
        if (i != 0){
            if (i >  highestElement){
                highestElement = i
                elementIndex = index
            }
        }
    }

    val token = "p50uh1m4ba"
    var firstResult =  if (highestElement != -1) separatedWors[elementIndex] else "-1"
        return removedToken(firstResult)
    }

fun removedToken(string: String):String{
    val token = "p50uh1m4ba"
    var result = string
    token.forEach { char ->
        result = result.toLowerCase().filterNot { it == char }
    }
    return result.ifEmpty { "EMPTY" }
}

fun smallest(numbers:Array<Int>):Int{
    var size = Int.MAX_VALUE
    return 0
}
//    println("elementIndex $elementIndex, highestElement  $highestElement")


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