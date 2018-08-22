package com.tokodeveloper.reactiontime.services

import com.tokodeveloper.reactiontime.models.GameModel
import com.tokodeveloper.reactiontime.models.Result

class GameServiceImpl(private val gameModel: GameModel) : GameService {

    override val state: HashMap<Int, String>
        get() = gameModel.state

    override val average: Int
        get() = gameModel.average

    override val active: Boolean
        get() = gameModel.active

    override val finished: Boolean
        get() = gameModel.finished

    override fun start(callback: (Boolean) -> Unit) {
        gameModel.start(callback)
    }

    override fun stop(callback: (Result) -> Unit) {
        gameModel.stop(callback)
    }

    override fun restart() {
        gameModel.restart()
    }


}