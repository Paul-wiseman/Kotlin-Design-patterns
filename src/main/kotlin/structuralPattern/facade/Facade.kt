package structuralPattern.facade

import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Provide a simple interface to a complex functionality
 *
 * Removes the need for complex object / memory management
 * Simplifies client implementation */

class ComplexSystemStore(private val filePath:String){

    private var cache = HashMap<String,String>()
    init {
        println("Reading data from the file:$filePath")
        cache = HashMap()
    }

    fun store(key:String, value: String){
        cache[key] = value
    }

    fun read(key: String) = cache[key]?:""

    fun comit() =  println("Storing data to file $filePath")
}

data class User(val login:String)

class UserRepository{
    private val systemPreference = ComplexSystemStore("/data/default.prefs")

    fun save(user: User){
        systemPreference.store("USER_KEY", user.login)
        systemPreference.comit()
    }

    fun findFirst():User = User(systemPreference.read("USER_KEY"))

    class FacadeTest{

        @Test
        fun testFacade(){
            val userRepo = UserRepository()
            val user = User("john")
            userRepo.save(user)

            val retrievedUser = userRepo.findFirst()

            Assertions.assertThat(retrievedUser.login).isEqualTo("john")
        }
    }
}