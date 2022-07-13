fun main(args: Array<String>) {
//    println("Hello World!")

    val stocks = listOf("Orange", "apple", "mango", "gauva")
    val builder = StringBuilder()
    builder.append("[")
    for (i in stocks.indices) {
        builder.append(stocks[i])
        if (i != stocks.size - 1) {
            builder.append(", ")
        }
    }
    builder.append("]")
//    println(stocks.joinToString(",","[","]"))

    val list1 = listOf(
        "1",
        "3",
        "6",
        "2",
    )
    val list2 = listOf(
        "4",
        "6",
        "2",
        "9",
        "11",
        "7",
    )

    println("union----${list1.union(list2)}")
    println("intersect----${list1.intersect(list2)}")
    println("subtract----${list1.subtract(list2)}")
    val p:List<paul> = listOf(1,3,2,4,45,5,6)
    println(p)
    ArrayDeque<paul>()
}

// Try adding program arguments via Run/Debug configuration.
// Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
//    println("Program arguments: ${args.joinToString()}")
typealias paul = Int