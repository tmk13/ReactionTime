package com.tokodeveloper.reactiontime.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tokodeveloper.reactiontime.models.Error
import com.tokodeveloper.reactiontime.models.GameModelImpl
import com.tokodeveloper.reactiontime.models.Result
import com.tokodeveloper.reactiontime.services.GameService
import com.tokodeveloper.reactiontime.services.GameServiceImpl

class GameViewModel : ViewModel() {

    private val TAG = "GameViewModel"

    private val gameService: GameService = GameServiceImpl(GameModelImpl(200))

    private var _result = MutableLiveData<Result>()
    private var _gameState = MutableLiveData<HashMap<Int, String>>()
    private var _correct = MutableLiveData<List<Boolean>>()
    private var _average = MutableLiveData<String>()
    private var _finished = MutableLiveData<Boolean>()
    private var _showError = MutableLiveData<Boolean>()
    private var _startVisible = MutableLiveData<Boolean>()
    private var _waitVisible = MutableLiveData<Boolean>()
    private var _stopVisible = MutableLiveData<Boolean>()
    private var _restartVisible = MutableLiveData<Boolean>()

    val result: LiveData<Result> = _result

    val state: LiveData<HashMap<Int, String>> = _gameState

    val correct: LiveData<List<Boolean>>

    val average: LiveData<String> = _average

    val finished: LiveData<Boolean> = _finished

    val showAverage: LiveData<Boolean>

    val showError: LiveData<Boolean> = _showError

    val startVisible: LiveData<Boolean> = _startVisible

    val waitVisible: LiveData<Boolean> = _waitVisible

    val stopVisible: LiveData<Boolean> = _stopVisible

    val restartVisible: LiveData<Boolean> = _restartVisible

    init {
        _startVisible.value = true
        _waitVisible.value = false
        _stopVisible.value = false
        _restartVisible.value = false
        _finished.value = false

        showAverage = Transformations.map(_average) { it.isNotEmpty() }
        correct = Transformations.map(_gameState) { it.values.map { !it.endsWith("!") } }
    }

    fun userClickedStartButton() {
        waitVisible()
        gameService.start {
            stopVisible()
        }
    }

    fun userClickedWaitButton() {
        gameService.restart()
        _gameState.postValue(gameService.state)
        showError()
        startVisible()
    }

    fun userClickedStopButton() {
        startVisible()

        gameService.stop {
            _result.value = it
            _gameState.postValue(gameService.state)
            checkError()
            setAverage()
            checkFinished()
        }
    }

    private fun checkError() {
        when (_result.value) {
            is Error -> showError()
            else -> {
            }
        }
    }

    private fun showError() {
        _average.postValue("")
        _showError.postValue(true)

        restartVisible()
    }

    private fun checkFinished() {
        val fin = gameService.finished
        if (fin) {
            _finished.postValue(fin)
            restartVisible()
        }
    }

    private fun setAverage() {
        val average = gameService.average
        if (average > 0) {
            _average.postValue(average.toString())
        } else {
            _average.postValue("")
        }
    }

    fun userClickedRestartButton() {
        startVisible()
        gameService.restart()
        _gameState.postValue(gameService.state)
        _correct.postValue(emptyList())
        setAverage()
    }

    private fun startVisible() {
        _startVisible.postValue(true)

        _waitVisible.postValue(false)
        _stopVisible.postValue(false)
        _restartVisible.postValue(false)
        _finished.postValue(false)
        _showError.postValue(false)
    }

    private fun waitVisible() {
        _waitVisible.postValue(true)

        _stopVisible.postValue(false)
        _startVisible.postValue(false)
        _restartVisible.postValue(false)
    }

    private fun stopVisible() {
        _stopVisible.postValue(true)

        _waitVisible.postValue(false)
        _startVisible.postValue(false)
        _restartVisible.postValue(false)
    }

    private fun restartVisible() {
        _restartVisible.postValue(true)

        _stopVisible.postValue(false)
        _waitVisible.postValue(false)
        _startVisible.postValue(false)
    }
}
