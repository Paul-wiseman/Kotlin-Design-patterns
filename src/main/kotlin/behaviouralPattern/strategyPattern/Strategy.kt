package behaviouralPattern

import org.junit.Test

/**
 * A class behaviour or algorithm can be changed at run time
 * object contain algorithm logic
 * context object that can handle algorithm objects
 * Useful when we want to be able to add functionality without changing program structure*/

class Printer(private val stringFormatterStrategy:(String) -> String){
    fun printString(message:String){
        println(stringFormatterStrategy(message))
    }
}

val lowerCaseFormatter = {it:String -> it.toLowerCase()}
val upperCaseFormatter = {it:String -> it.toUpperCase()}

class StrategyTest{
    @Test
    fun testStrategy(){
        val inputString = "LOREM ipsum DOLOR sit amet"

        val lowerCasePrinter = Printer(lowerCaseFormatter)
        lowerCasePrinter.printString(inputString)

        val upperCasePrinter = Printer(upperCaseFormatter)
        upperCasePrinter.printString(inputString)
    }
}