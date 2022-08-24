package codelabs

class FillInTheBlankQuestion(
    val questionText: String,
    val answer: String,
    val difficulty: String
)

class TrueOrFalseQuestion(
    val questionText: String,
    val answer: Boolean,
    val difficulty: String
)

class NumericQuestion(
    val questionText: String,
    val answer: Int,
    val difficulty: String
)

data class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)

enum class Difficulty{
    EASY, MEDIUM, HARD
}

fun main() {

//    println(Quiz.total)
//    println("${Quiz.answered} of ${Quiz.total} answered.")
//    println(Quiz.progressText)
    Quiz.printProgressBar()
    val quiz = Quiz()
    quiz.printQuiz()
}

class Quiz{
    private val question1 = Question("Quoth the raven _____", "nevermore", Difficulty.MEDIUM)
    private val question2 = Question("The sky is green. True or false", false, Difficulty.EASY)
    private val question3 = Question("How many days are there between full moons?", 28, Difficulty.HARD)


   companion object StudentProgress{
        var total:Int = 10
        var answered:Int = 3
    }

    fun printQuiz() {
        question1.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question2.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question3.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
    }
}

val Quiz.StudentProgress.progressText:String
get() = "$answered of $total answered"

fun Quiz.StudentProgress.printProgressBar() {
    repeat(answered){ print("▓") }
    repeat(total - answered){ print("▒") }
    println()
    println(progressText)
}