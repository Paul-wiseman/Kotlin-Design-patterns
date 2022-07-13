package creationalPattern.builderPattern.test

import creationalPattern.builderPattern.Component
import org.assertj.core.api.Assertions
import org.junit.Test

class ComponentTest{

    @Test
    fun builderTest(){
        val component = Component.Builder()
            .setParam1("Some value")
            .setParam2(2)
            .build()

        println(component)
        println(component.param1)
        println(component.param2)
        println(component.param3)
        Assertions.assertThat(component.param1).isEqualTo("Some value")
        Assertions.assertThat(component.param2).isEqualTo(2)
        Assertions.assertThat(component.param3).isEqualTo(null)
    }
}