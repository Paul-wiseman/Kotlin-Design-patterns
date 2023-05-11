package designPatterns.creationalPattern.builderPattern

import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Used when wwe have have multiple parameters to initialize.
 * Kotlin solves this problem with named parameters
 * both however this solution doesn't work with java code
 * because JVM is not familiar with named parameter
 * */

class Building private constructor(
    val garage: String? = null,
    val swimmingPool: Int? = null,
    val garden: Boolean? = null
) {


    class Builder {
        private var garage: String? = null
        private var swimmingPool: Int? = null
        private var garden: Boolean? = null

        fun garage(garage: String) = apply { this.garage = garage }
        fun swimmingPool(swimmingPool: Int) = apply { this.swimmingPool = swimmingPool }
        fun garden(garden: Boolean) = apply { this.garden = garden }
        fun build() = Building(
            garage = garage,
            swimmingPool = swimmingPool,
            garden = garden
        )
    }
}

class BuildingTest {
    @Test
    fun builderTest() {
        // given
        val garage = "Some Value"
        val garden = true

        // when
        val building = Building.Builder()
            .garage(garage)
            .garden(garden)
            .build()

        //then
        Assertions.assertThat(building.garage).isEqualTo(garage)
        Assertions.assertThat(building.garden).isEqualTo(garden)
        Assertions.assertThat(building.swimmingPool).isEqualTo(null)

    }
}

data class ExiConfigData(
    val token: String,
    val apiKey: String,
    val isMockEnabled: Boolean
)

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

class Cat() : ISoundBehavior by MeowSound() {
    override fun makeSound() {
        println("Modified Meow!")
    }

    init {
        makeSound()
    }
}


data class PC(
    val motherboard: String = "Terasus XZ27",
    val cpu: String = "Until Atom K500",
    val ram: String = "8GB Microcend BBR5",
    val graphicCard: String = "nKCF 8100TZ"
)

class PCTest {

    @Test
    fun prototypeTest(){
        // given
        val motherboard = "Terasus XZ27"
        val cpu = "Until Atom K500"
        val ram = "8GB Microcend BBR5"
        val graphicCard = "nKCF 8100TZ"

        val hp = PC(
            motherboard = motherboard,
            cpu = cpu,
            ram = ram,
            graphicCard = graphicCard
        )

        // when
        val samsung = hp.copy(graphicCard = "nKCF 8999ZTXX", ram = "16GB BBR6")

        // then
        Assertions.assertThat(samsung.motherboard).isEqualTo(motherboard)
        Assertions.assertThat(samsung.cpu).isEqualTo(cpu)
        Assertions.assertThat(samsung.ram).isEqualTo("16GB BBR6")
        Assertions.assertThat(samsung.graphicCard).isEqualTo("nKCF 8999ZTXX")
    }
}


fun main() {
    println(azSolution(intArrayOf(1, 1, 2, 3, 4, 5, 6, 6), 6))
    println(airPlaneSeatReservation(2, "1A 2F 1C"))
    println(airPlaneSeatReservation(1, " "))

    val samsungPC = PC().copy(
        graphicCard = "nKCF 8999ZTXX",
        ram = "16GB BBR6"
    )

    println(samsungPC)

}

interface Prototype {
    fun clone(): Prototype
}

data class ConcretePrototype(val property1: String, val property2: Int) : Prototype {
    override fun clone(): Prototype {
        return copy()
    }
}

class PrototypeManager {
    private val prototypes = mutableMapOf<String, Prototype>()

    fun registerPrototype(key: String, prototype: Prototype) {
        prototypes[key] = prototype
    }

    fun getPrototype(key: String): Prototype? {
        return prototypes[key]?.clone()
    }
}

suspend fun networkCall1() {
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
        if (!d.contains("${index}B") && !d.contains("${index}C") && !d.contains("${index}D") && !d.contains(
                "${index}E"
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



