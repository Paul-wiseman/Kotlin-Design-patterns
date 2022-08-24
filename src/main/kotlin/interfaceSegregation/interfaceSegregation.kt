package interfaceSegregation

interface BasicScoreboardIf{
    fun showMatchStarted()
    fun showMatchFinished()
}

interface FootballScoreboardIf{
    fun showGoalScored()
}
interface BasketballScoreboardIf{
    fun showPeriodStarted()
    fun showPeriodEnded()
}

class FootballScoredBoard:BasicScoreboardIf, FootballScoreboardIf{
    override fun showMatchStarted() {
        print("football match started")
    }

    override fun showMatchFinished() {
        print("football match end")
    }

    override fun showGoalScored() {
        print("goal scored!")
    }
}

class TennisScoredBoard:BasicScoreboardIf{
    override fun showMatchStarted() {
        print("football match started")
    }

    override fun showMatchFinished() {
        print("football match end")
    }
}

class BasketballScoredBoard():BasicScoreboardIf, BasketballScoreboardIf{
    override fun showMatchStarted() {
        print("football match started")
    }

    override fun showMatchFinished() {
        print("football match end")
    }

    override fun showPeriodStarted() {
        print("basketball period started")
    }

    override fun showPeriodEnded() {
        print("basketball period ended")
    }
}




