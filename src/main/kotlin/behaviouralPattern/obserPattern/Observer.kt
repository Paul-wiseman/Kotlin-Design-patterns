package behaviouralPattern.obserPattern

import java.io.File

/**
 * Define a subscription mechanism
 * Notify multiple objects simultaneously*/

interface EventListener{
    fun update(eventType:String?, file:File)
}

class EventManager(vararg operations:String){
    var listeners = hashMapOf<String, ArrayList<EventListener>>()

    init {
        for (operation in operations){
            listeners[operation] = ArrayList<EventListener>()
        }
    }

    fun subscribe(eventType: String?, listener: EventListener){

    }
}


class Editor