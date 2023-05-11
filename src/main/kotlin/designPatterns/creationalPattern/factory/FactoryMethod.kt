package designPatterns.creationalPattern.factory

import org.junit.Assert
import org.junit.Test

/**
 * Factory method
 * Provides a way to access functionality without caring about implementation
 *
 * this provides separation of concerns
 * allows for testability
 * */

sealed class Country {
    object Canada : Country()
    object Spain : Country()
    class Greece(val someProperty: String) : Country()
    data class USA(val someProperty: String) : Country()
}

class Currency(val code: String)


object CurrencyFactory {
    fun currencyForCountry(country: Country): Currency =
        when (country) {
            is Country.Canada -> Currency("CAD")
            is Country.Greece -> Currency("EUR")
            is Country.Spain -> Currency("EUR")
            is Country.USA -> Currency("USD")
            else -> throw IllegalArgumentException()
        }
}


class FactoryMethodTest {
    @Test
    fun currencyTest() {
        val greekCurrency = CurrencyFactory.currencyForCountry(Country.Greece("")).code
        println("Greek currency: $greekCurrency")

        val usaCurrency = CurrencyFactory.currencyForCountry(Country.USA("")).code
        println("Greek currency: $greekCurrency")

        Assert.assertEquals(greekCurrency, "EUR")
        Assert.assertEquals(usaCurrency, "USD")

    }
}

// 1
interface HostingPackageInterface {
    fun getServices(): List<String>
}

// 2
enum class HostingPackageType {
    STANDARD,
    PREMIUM
}

// 3
class StandardHostingPackage : HostingPackageInterface {
    override fun getServices(): List<String> {
        return listOf("Shared hosting", "Cloud hosting", "Dedicated server hosting")
    }
}

// 4
class PremiumHostingPackage : HostingPackageInterface {
    override fun getServices(): List<String> {
        return listOf("High-performance hosting","Enterprise-level hosting")
    }
}

// 5
object HostingPackageFactory {
    // 6
    fun getHostingFrom(type: HostingPackageType): HostingPackageInterface {
        return when (type) {
            HostingPackageType.STANDARD -> {
                StandardHostingPackage()
            }
            HostingPackageType.PREMIUM -> {
                PremiumHostingPackage()
            }
        }
    }
}

class HostingPackageTest {

    @Test
    fun getHostingPackageTypeTest() {
        // give
        val packageType = HostingPackageType.PREMIUM

        // when
        val result = HostingPackageFactory.getHostingFrom(packageType)

        //then
        Assert.assertEquals(result.getServices(), PremiumHostingPackage().getServices())
    }
}


interface Animal {
    val name: String
}

class Cat : Animal {
    override val name = "Cat"
}

class Dog : Animal {
    override val name = "Dog"
}

class AnimalFactory {

    fun createAnimal(animalType: String): Animal {

        return when (animalType.trim().toLowerCase()) {
            "cat" -> Cat()
            "dog" -> Dog()
            else -> throw RuntimeException("Unknown animal $animalType")
        }
    }
}


class AnimalFactoryTest {

    @Test
    fun main() {
        // given
        val animalTypes = "dog"
        // when
        val factory = AnimalFactory()
        val animal = factory.createAnimal(animalTypes)

        //then
        Assert.assertEquals(animal.name, "Dog")
        Assert.assertEquals(animal.javaClass, Dog::class.java)
    }
}


