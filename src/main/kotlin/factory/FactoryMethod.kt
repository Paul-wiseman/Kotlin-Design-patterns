package factory

import org.jetbrains.annotations.TestOnly
import org.junit.Assert
import org.junit.Test

/**
 * Factory method
 * Provides a way to access functionality without caring about implementation
 *
 * this provides separation of concerns
 * allows for testability
 * */

sealed class Country{
    object Canada:Country()
}

object Spain:Country()
class Greece(val someProperty:String):Country()
data class USA(val someProperty:String):Country()
class Currency(val code:String)


object CurrencyFactory{
    fun currencyForCountry(country: Country):Currency =
        when(country){
            is Country.Canada -> Currency("CAD")
            is Greece -> Currency("EUR")
            is Spain -> Currency("EUR")
            is USA -> Currency("USD")
        }
}


class FactoryMethodTest{
    @Test
    fun currencyTest(){
        val greekCurrency = CurrencyFactory.currencyForCountry(Greece("")).code
        println("Greek currency: $greekCurrency")

        val usaCurrency = CurrencyFactory.currencyForCountry(USA("")).code
        println("Greek currency: $greekCurrency")

        Assert.assertEquals(greekCurrency,"EUR")
        Assert.assertEquals(usaCurrency,"USD")

    }
}