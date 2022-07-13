package structuralPattern.decoratorPattern

import org.junit.Test

/**
 * Attach new behaviour to an object
 * without altering existing code
 * overriding behaviour
 * */

interface CoffeeMachine {
    fun makeSmallCoffee()
    fun makeLargeCoffee()
}

class NormalCoffeeMachine : CoffeeMachine {
    override fun makeSmallCoffee() {
        println("Normal coffee machine: Making small coffee")
    }

    override fun makeLargeCoffee() {
        println("Normal coffee machine: Making large coffee")
    }
}

// Decorator
class EnhancedCoffeeMachine(private val coffeeMachine: CoffeeMachine) : CoffeeMachine by coffeeMachine {
    override fun makeLargeCoffee() {
        println("Enhanced coffee machine: making large coffee")
    }

    fun makeMilkCoffee() {
        println("Enhanced coffee machine: making milk coffee")
        coffeeMachine.makeSmallCoffee()
        println("Enhanced coffee machine: Adding milk")
    }
}

class DecoratorTest() {

    @Test
    fun testDecorator() {
        val normalCoffeeMachine = NormalCoffeeMachine()
        val enhancedCoffeeMachine = EnhancedCoffeeMachine(normalCoffeeMachine)

        enhancedCoffeeMachine.makeSmallCoffee()
        println("---------------------")
        enhancedCoffeeMachine.makeLargeCoffee()
        println("---------------------")
        enhancedCoffeeMachine.makeMilkCoffee()


    }
}