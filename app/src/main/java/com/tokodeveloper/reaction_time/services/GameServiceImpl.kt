package com.tokodeveloper.reaction_time.services

import com.tokodeveloper.reaction_time.models.Error
import com.tokodeveloper.reaction_time.models.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class GameServiceImpl(private val minimumTime: Long) : GameService {

    private val rand = Random()
    private var timer = Timer()

    private val _gameState = linkedMapOf<Int, String>()
    private var _currentTimeId = 0
    private var _startTime = 0L
    private var _average = 0
    private var _activated = false
    private var _finished = false

    companion object {
        private const val TAG = "GameServiceImpl"
    }

    override val state: HashMap<Int, String>
        get() = _gameState

    override val average: Int
        get() = _average

    override val active: Boolean
        get() = _activated

    override val finished: Boolean
        get() = _finished

    override suspend fun start() = withContext(Dispatchers.Default) {
        _startTime = 0
        _activated = false

        val randomTime = (((rand.nextInt(2) + rand.nextFloat()) + 2) * 1_000).toLong()

        delay(randomTime)

        _startTime = System.nanoTime()
        _activated = true
    }

    override suspend fun stop() = withContext(Dispatchers.Default) {
        val stopTime = (System.nanoTime() - _startTime) / 1_000_000

        when {
            stopTime >= minimumTime -> {
                _gameState += ++_currentTimeId to stopTime.toString()
                computeAverage()
                checkFinished()
                Success(stopTime)
            }
            else -> {
                _gameState += ++_currentTimeId to stopTime.toString() + "!"
                Error()
            }
        }
    }

    private fun checkFinished() {
        _finished = _gameState.size == 5
    }

    private fun computeAverage() {
        val sum = _gameState.values.map { it.toInt() }.sum()
        _average = sum / _gameState.size
    }

    override fun restart() {
        timer.cancel()
        resetState()
    }

    private fun resetState() {
        _startTime = 0
        _currentTimeId = 0
        _average = 0
        _activated = false
        _finished = false
        _gameState.clear()
    }
}