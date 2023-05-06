package designPatterns.creationalPattern.builderPattern.test

import designPatterns.creationalPattern.builderPattern.Component
import designPatterns.creationalPattern.builderPattern.ExiConfigData
import org.junit.Test

class ComponentTest {

    @Test
    fun builderTest() {
        val component = Component.Builder()
            .setExiConfig(
                ExiConfigData(
                    token = "jlhdlhfjlodhfga",
                    apiKey = "jlashdnfopahsdfiofw",
                    false
                )
            )
//            .setParam2(2)
            .build()


//        println(component.param1)
//        println(component.param2)
//        println(component.param3)
//        Assertions.assertThat(component.param1).isEqualTo("Some value")
//        Assertions.assertThat(component.param2).isEqualTo(2)
//        Assertions.assertThat(component.param3).isEqualTo(null)
    }
}