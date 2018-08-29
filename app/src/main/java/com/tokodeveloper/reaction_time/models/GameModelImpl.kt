package com.tokodeveloper.reaction_time.models

import java.util.*
import kotlin.concurrent.timerTask

class GameModelImpl(private val minimumTime: Long) : GameModel {

    private val TAG = "GameModelImpl"

    private val rand = Random()
    private var timer = Timer()

    private val _gameState = linkedMapOf<Int, String>()
    private var _currentTimeId = 0
    private var _startTime = 0L
    private var _average = 0
    private var _activated = false
    private var _finished = false

    override val state: HashMap<Int, String>
        get() = _gameState

    override val average: Int
        get() = _average

    override val active: Boolean
        get() = _activated

    override val finished: Boolean
        get() = _finished

    override fun start(callback: (Boolean) -> Unit) {
        _startTime = 0
        _activated = false

        val randomTime = (((rand.nextInt(2) + rand.nextFloat()) + 2) * 1_000).toLong()
        timer = Timer()
        timer.schedule(timerTask {
            _startTime = System.nanoTime()
            _activated = true
            callback(_activated)
        }, randomTime)
    }

    override fun stop(callback: (Result) -> Unit) {
        val stopTime = (System.nanoTime() - _startTime) / 1_000_000

        return when {
            stopTime >= minimumTime -> {
                _gameState += ++_currentTimeId to stopTime.toString()
                computeAverage()
                checkFinished()
                callback(Success(stopTime))
            }
            else -> {
                _gameState += ++_currentTimeId to stopTime.toString() + "!"
                callback(Error())
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