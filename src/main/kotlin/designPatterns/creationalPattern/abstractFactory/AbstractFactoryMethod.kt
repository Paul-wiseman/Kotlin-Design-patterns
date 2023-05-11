package designPatterns.creationalPattern.abstractFactory

import org.assertj.core.api.Assertions
import org.junit.Test


/**
 * Abstract Factory method
 *
 * Provides a way to access functionality without caring
 * about implementation
 *
 * It is one level of abstraction over the factory pattern.
 *
 * Separation of concern
 *
 * Allows for testability
 * */

interface DataSource{
    fun getData():List<String>
}

class DatabaseDataSource : DataSource {
    override fun getData(): List<String> {
        return listOf("Motherboard","CPU")
    }
}

class NetworkDataSource : DataSource {
    override fun getData(): List<String> {
        return listOf("Graphical card","RAM")
    }
}

abstract class DataSourceFactory {

    abstract fun makeDataSource(): DataSource

    companion object {
        inline fun <reified T : DataSource> createFactory(): DataSourceFactory =
            when (T::class) {
                DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}

class NetworkFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()
}

class DatabaseFactory : DataSourceFactory() {
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}

class AbstractFactoryMethodTest {
    @Test
    fun afTest() {
        val dataSourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource: DataSource = dataSourceFactory.makeDataSource()
        println("Created datasource :$dataSource")
        Assertions.assertThat(dataSource).isInstanceOf(DatabaseDataSource::class.java)
    }
}


interface Button {
    fun paint()
}

class WindowsButton : Button {
    override fun paint() {
        println("Windows button painted")
    }
}

class MacOSButton : Button {
    override fun paint() {
        println("MacOS button painted")
    }
}

interface GUIFactory {
    fun createButton(): Button?
}

class WindowsFactory : GUIFactory {
    override fun createButton(): Button {
        return WindowsButton()
    }
}

class MacOSFactory : GUIFactory {
    override fun createButton(): Button {
        return MacOSButton()
    }
}

class Application(private val factory: GUIFactory) {
    private var button: Button? = null
    fun createUI(): Button? {
        button = factory.createButton()
        return button
    }

    fun paint() {
        button?.paint()
    }
}

val ex by lazy {
    println("Abc")
    "BMW"
}

class ButtonFactoryTest{
    @Test
    fun buttonFactoryTest(){
        //given
        val factory: GUIFactory = MacOSFactory()
        // when
        val app = Application(factory).createUI()

        //then
        Assertions.assertThat(app).isInstanceOf(MacOSButton::class.java)
    }
}
object Client {
    @JvmStatic
    fun main(args: Array<String>) {
        var factory: GUIFactory = MacOSFactory()
        var app = Application(factory)
        app.createUI()
        app.paint()
        factory = WindowsFactory()
        app = Application(factory)
        app.createUI()
        app.paint()
        println(ex)
        println(ex)
        println(ex)
        println(ex)
        println(ex)
        println(ex)

    }
}


// another abstract factory
interface Building<in UnitType, out ProducedUnit> where UnitType : Enum<*>, ProducedUnit : Unit {
    fun build(type: UnitType): ProducedUnit
}

class HQ {
    val buildings = mutableListOf<Building<*, Unit>>()
//    fun buildBarracks(): Barracks {
//        val b = Barracks()
//        buildings.add(b)
//        return b
//    }

//    fun buildVehicleFactory(): VehicleFactory {
//        val vf = VehicleFactory()
//        buildings.add(vf)
//        return vf
//    }

    fun buildBarracks(): Building<InfantryUnits, Infantry> {
        val b = object : Building<InfantryUnits, Infantry> {
            override fun build(type: InfantryUnits): Infantry {
                return when (type) {
                    InfantryUnits.RIFLEMEN -> Rifleman()
                    InfantryUnits.ROCKET_SOLDIER -> RocketSoldier()
                }
            }
        }
        buildings.add(b)
        return b
    }


    fun buildVehicleFactory(): Building<VehicleUnits, Vehicle> {
        val vf = object : Building<VehicleUnits, Vehicle> {
            override fun build(type: VehicleUnits) = when (type) {
                VehicleUnits.APC -> APC()
                VehicleUnits.TANK -> Tank()
            }
        }
        buildings.add(vf)
        return vf }
}

interface Unit
interface Vehicle : Unit
interface Infantry : Unit

class Rifleman : Infantry
class RocketSoldier : Infantry
enum class InfantryUnits {
    RIFLEMEN,
    ROCKET_SOLDIER
}

class APC : Vehicle
class Tank : Vehicle
enum class VehicleUnits {
    APC,
    TANK
}

class Barracks : Building<InfantryUnits, Infantry> {
    override fun build(type: InfantryUnits): Infantry {
        return when (type) {
            InfantryUnits.RIFLEMEN -> Rifleman()
            InfantryUnits.ROCKET_SOLDIER -> RocketSoldier()
        } }
}

class VehicleFactory : Building<VehicleUnits, Vehicle> {
    override fun build(type: VehicleUnits) = when (type) {
        VehicleUnits.APC -> APC()
        VehicleUnits.TANK -> Tank()
    }
}

val hq = HQ()
val barracks1 = hq.buildBarracks()
val barracks2 = hq.buildBarracks()
val vehicleFactory1 = hq.buildVehicleFactory()


val units = listOf(
    barracks1.build(InfantryUnits.RIFLEMEN),
    barracks2.build(InfantryUnits.ROCKET_SOLDIER),
    barracks2.build(InfantryUnits.ROCKET_SOLDIER),
    vehicleFactory1.build(VehicleUnits.TANK),
    vehicleFactory1.build(VehicleUnits.APC),
    vehicleFactory1.build(VehicleUnits.APC)
)



