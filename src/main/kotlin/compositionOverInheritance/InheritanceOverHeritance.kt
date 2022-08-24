package compositionOverInheritance

open class Animal{
    fun eat(){}
}

class Dog:Animal(){
    fun back(){}
}

class Cat:Animal(){
    fun meow(){}
}

open class Robot:Animal(){
    fun drive(){
        eat()
    }

}

class CleaningRobot:Robot(){
    fun clean(){}
}

class FeedingRobot:Robot(){

}