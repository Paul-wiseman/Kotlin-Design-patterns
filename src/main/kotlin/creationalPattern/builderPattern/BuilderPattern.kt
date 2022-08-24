package creationalPattern.builderPattern

import org.assertj.core.internal.bytebuddy.agent.builder.AgentBuilder

/**
 * Used when wwe have have multiple parameters to initialize.
 * Kotlin solves this problem with named parameters
 * both however this solution doesn't work with java code
 * because JVM is not familiar with named parameter
 * */

class Component private constructor(builder: Builder){
    var param1:String? = null
    var param2:Int? = null
    var param3:Boolean? = null

    class Builder{
       private var param1:String? = null
       private var param2:Int? = null
       private var param3:Boolean? = null

        fun setParam1(param1:String) = apply { this.param1 = param1 }
        fun setParam2(param2:Int) = apply { this.param2 = param2 }
        fun setParam3(param3:Boolean) = apply { this.param3 = param3 }
        fun build() = Component(this)

        fun getParam1() = param1
        fun getParam2() = param2
        fun getParam3() = param3
    }

    init {
        param1 = builder.getParam1()
        param2 = builder.getParam2()
        param3 = builder.getParam3()
    }
}

interface ISoundBehavior {
    fun makeSound()
    fun eat()
}

class MeowSound : ISoundBehavior {
    override fun makeSound() {
        println("Meow!")
    }

    override fun eat() {
        println("Eating fish!")
    }
}

class Cat():ISoundBehavior by MeowSound() {
    override fun makeSound() {
        println("Modified Meow!")
    }
   init {
       makeSound()
   }
}

fun main() {
    println(azSolution(intArrayOf(1, 1, 2, 3, 4, 5, 6, 6), 6))
    println(airPlaneSeatReservation(2, "1A 2F 1C"))
    println(airPlaneSeatReservation(1, " "))



}
fun k(){

}
suspend fun networkCall1(){
    println("perform network call")
}
fun azSolution(A: IntArray, K: Int): Boolean {
    val n = A.size
    for (i in 0 until n - 1) {
        if (A[i] + 1 < A[i + 1]) return false
    }
    return !(A[0] != 1 || A[n - 1] != K)
}

fun airPlaneSeatReservation(N: Int, S: String): Int {
    var c = 0
    var d = S.split(' ').toMutableList()
    for (index in 1..N) {
        if (!d.contains("${index}B") && !d.contains("${index}C") && !d.contains("${index}D") && !d.contains("${index}E"
            )
        ) {
            c += 1
            d.add("${index}E")
        }

        if (!d.contains("${index}D") && !d.contains("${index}E") && !d.contains("${index}F") && !d.contains(
                "${index}G"
            )
        ) {
            c += 1
            d.add("${index}G")
        }
        if (!d.contains("${index}F") && !d.contains("${index}G") && !d.contains("${index}H") && !d.contains(
                "${index}I"
            )
        ) {
            c += 1
        }

    }
    return c
}



