package designPatterns.creationalPattern.builderPattern.test

import designPatterns.creationalPattern.builderPattern.Building
import org.assertj.core.api.Assertions
import org.junit.Test

class BuildingTest {

    @Test
    fun builderTest() {
        val building = Building.Builder()
            .swimmingPool(2)
            .build()


        println(building.garage)
        println(building.swimmingPool)
        println(building.garden)
        Assertions.assertThat(building.garage).isEqualTo("Some value")
        Assertions.assertThat(building.swimmingPool).isEqualTo(2)
        Assertions.assertThat(building.garden).isEqualTo(null)
    }
}