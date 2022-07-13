package structuralPattern.composite

import org.assertj.core.api.Assertions
import org.junit.Test


/**
 * Compose object into tree structures
 *
 * works when the core functionality can be represented as a tree
 *
 * manipulate many objects as a single one */

open class Equipment(
    open val price:Int,
    val name:String)

open class Composite(name: String):Equipment(0,name){
    private val equipments = ArrayList<Equipment>()

    override val price:Int
    get() = equipments.map { it.price }.sum()

    fun add(equipment: Equipment): Composite = apply { equipments.add(equipment) }
}

class Computer:Composite("PC")
class Processor:Equipment(1000,"Processor")
class HardDrive:Equipment(250,"Hard Drive")
class Memory:Composite("Memory")
class ROM:Equipment(100,"Read only Memory")
class RAM:Equipment(75,"Random Access Memory")

class CompositeTest{
    @Test
    fun testComposite(){
        val memory = Memory()
            .add(ROM()).add(RAM())
        val pc = Computer()
            .add(memory)
            .add(Processor())
            .add(HardDrive())

        println("PC price: ${pc.price}")

        Assertions.assertThat(pc.name).isEqualTo("PC")
        Assertions.assertThat(pc.price).isEqualTo(1425)
    }
}

interface InfantryUnit
class Rifleman : InfantryUnit
class Sniper : InfantryUnit


class Squad(val infantryUnits: MutableList<InfantryUnit> =
                mutableListOf()) {
    constructor(vararg units: InfantryUnit) : this(mutableListOf()) {
        for (u in units) {
            this.infantryUnits.add(u)
        }
    } }

//fun main(){
//    val miller = Rifleman()
//    val caparzo = Rifleman()
//    val jackson = Sniper()
//    val squad = Squad(miller, caparzo).infantryUnits.size
//    println(squad)
//
//
////    squad.infantryUnits.add(miller)
////    squad.infantryUnits.add(caparzo)
////    squad.infantryUnits.add(jackson)
////    println(squad.infantryUnits.size) // Prints 3
//}
