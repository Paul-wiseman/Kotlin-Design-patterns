package interfaceSegregation

interface ScoreboardEventListener {
    fun showGoalScored()
    fun showRedCard()
    fun showYellowCard()
    fun showPeriodStarted()
    fun showPeriodEnded()
}

interface FootballScoreboardEventListener {
    fun showGoalScored()
    fun showRedCard()
    fun showYellowCard()
}

class EventDispatcher {
    lateinit var listener: ScoreboardEventListener

    fun registerListener(listener: ScoreboardEventListener) {
        this.listener = listener
    }
}

class FootballEventDispatcher : ScoreboardEventListener {
    private val dispatcher = EventDispatcher()

    init {
        dispatcher.registerListener(this)
    }

    lateinit var listener: FootballScoreboardEventListener

    fun registerListener(listener: FootballScoreboardEventListener) {
        this.listener = listener
    }

    override fun showGoalScored() {
        listener.showGoalScored()
    }

    override fun showRedCard() {
        listener.showRedCard()
    }

    override fun showYellowCard() {
        listener.showYellowCard()
    }

    override fun showPeriodStarted() {
        TODO("Not yet implemented")
    }

    override fun showPeriodEnded() {
        TODO("Not yet implemented")
    }
}


class FootballScoreboard : FootballScoreboardEventListener {
    private val dispatcher = FootballEventDispatcher()

    init {
        dispatcher.registerListener(this)
    }

    override fun showGoalScored() {
        print("goal scored")
    }

    override fun showRedCard() {
        print("red card")
    }

    override fun showYellowCard() {
        print("yellow card")
    }

}

class BasketballScoreboard : ScoreboardEventListener {
    private val dispatcher = EventDispatcher()

    init {
        dispatcher.registerListener(this)
    }

    override fun showGoalScored() {

    }

    override fun showRedCard() {

    }

    override fun showYellowCard() {

    }

    override fun showPeriodStarted() {
        print("period started")
    }

    override fun showPeriodEnded() {
        print("period ended")
    }

}