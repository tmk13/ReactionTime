package com.tokodeveloper.reaction_time.services

import com.tokodeveloper.reaction_time.models.GameModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameServiceImpl @Inject constructor(private val gameModel: GameModel) : GameService {

    override val state: HashMap<Int, String>
        get() = gameModel.state

    override val average: Int
        get() = gameModel.average

    override val active: Boolean
        get() = gameModel.active

    override val finished: Boolean
        get() = gameModel.finished

    override suspend fun start() {
        gameModel.start()
    }

    override suspend fun stop() = gameModel.stop()

    override fun restart() {
        gameModel.restart()
    }


}