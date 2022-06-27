package builderPattern

import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Used when wwe have have multiple parameters to initialize.
 * Kotlin solves this problem with named parameters
 * both however this solution doesn't work with java code
 * because JVM is not familiar with named parameter
 * */

class Component private constructor(builder:Builder){
    var param1:String? = null
    var param2:Int? = null
    var param3:Boolean? = null

    class Builder{
       private var param1:String? = null
       private var param2:Int? = null
       private var param3:Boolean? = null

        fun setParam1(param1:String) = apply { this.param1 = param1 }
        fun setParam2(param2:Int) = apply { this.param2 = param2 }
        fun setParam3(param3:Boolean) = apply { this.param3 = param3 }
        fun build() = Component(this)

        fun getParam1() = param1
        fun getParam2() = param2
        fun getParam3() = param3
    }

    init {
        param1 = builder.getParam1()
        param2 = builder.getParam2()
        param3 = builder.getParam3()
    }
}

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