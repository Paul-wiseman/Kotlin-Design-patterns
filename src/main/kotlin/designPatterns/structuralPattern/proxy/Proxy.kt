package designPatterns.structuralPattern.proxy

import org.junit.Test

/**
 * Provide some functionality before and/or after calling an object
 * Similar to a facade, except the proxy has the interface
 * Similar to decorator, except the proxy manages the lifecycle of its object*/


interface Image{
    fun display()
}

class RealImage(private val filename:String): Image {
    override fun display() {
        println("RealImage: Displaying $filename")
    }

    private fun loadFromDisk(filename: String){
        println("RealImage: Loading $filename")
    }

    init {
        loadFromDisk(filename)
    }
}

class ProxyImage(private val filename:String): Image {
    private var realImage: RealImage? = null
    override fun display() {
        println("ProxyImage: Displaying $filename")
        if (realImage == null){
            realImage = RealImage(filename)
        }
        realImage!!.display()
    }
}

class ProxyTest{
    @Test
    fun testProxy(){
        val image = ProxyImage("test.jpg")

        image.display()
        println("--------------------")

        image.display()
    }
}