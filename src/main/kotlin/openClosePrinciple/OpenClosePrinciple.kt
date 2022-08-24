package openClosePrinciple

import kotlin.random.Random

/**
 * A software entity (class, function etc) should be open for extension, but closed for modification
 *
 * Bertrand Mayer, 1988, Object-Oriented Software Construction
 * write code so that you will be able to add new functionality without changing the existing code
 *
 * A class is Closed, Since it may be Compiled stored in a library, baselined and used by client classes
 *
 * BUT it is also open since any new class may use it as parent, adding new features.
 * when a descendant is defined, there is no need to charge the original or to disturb its clients
 *
 * Proposes to use inheritance
 *
 *
 * Robert C. Martin's Version
 *
 * polymorphic Open - Closed Principle
 *
 * Abstractions. Our module depend on fixed abstractions that can be implemented by an infinite number of implementations.
 * Closed for modification since they only depend on the fixed abstraction, but open for extension as wwe
 * can always replace the current derivative with a new one
 * */


abstract class PowerUp{
    abstract var basePower:Int
    abstract fun consume(initialHealth:Int):Int
}

class Potion :PowerUp(){
    override var basePower = 15
    private val fat = 2
    override fun consume(initialHealth: Int): Int = initialHealth + basePower - fat
}

class EnergyDrink:PowerUp(){
    override var basePower = 20
    private val poisoningProbability = 30
    override fun consume(initialHealth: Int): Int {
        var healthToReturn = initialHealth+basePower
       if (Random.nextInt(100)< poisoningProbability) {
           healthToReturn = 5
       }

        return healthToReturn
}
}

class Pills:PowerUp(){
    override var basePower = 10
    override fun consume(initialHealth: Int): Int =
        initialHealth+basePower

}
class MedicalKit:PowerUp(){
    override var basePower = 20
    private val iron = 4
    private val protein = 6
    override fun consume(initialHealth: Int): Int =
        initialHealth + basePower * protein + iron

}
class Player {
    var health: Int = 100

    fun getPowerUp(powerUp: PowerUp) {
        health = powerUp.consume(health)

    }
}








/**-------old implementation-----*/


//abstract class PowerUp{
//    abstract var basePower:Int
//}
//
//class Potion :PowerUp(){
//    override var basePower = 15
//    val fat = 2
//}
//
//class EnergyDrink:PowerUp(){
//    override var basePower = 20
//    val poisoningProbability = 30
//}
//class MedicalKit:PowerUp(){
//    override var basePower = 20
//    val iron = 4
//    val protein = 6
//}
//class Player{
//    var health:Int=100
//
//    fun getPowerUp(powerUp: PowerUp){
//        when(powerUp) {
//            is Potion ->{
//                health += powerUp.basePower - powerUp.fat
//            }
//            is EnergyDrink ->{
//                health += powerUp.basePower
//                if (Random.nextInt(100)>powerUp.poisoningProbability){
//                    health = 5
//                }
//            }
//            is MedicalKit ->{
//                health+=powerUp.basePower + powerUp.iron
//            }
//        }
//    }
//}



