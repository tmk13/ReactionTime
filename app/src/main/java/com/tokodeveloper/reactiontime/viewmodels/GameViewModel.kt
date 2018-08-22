package com.tokodeveloper.reactiontime.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tokodeveloper.reactiontime.models.Error
import com.tokodeveloper.reactiontime.models.GameModelImpl
import com.tokodeveloper.reactiontime.models.Result
import com.tokodeveloper.reactiontime.models.Success
import com.tokodeveloper.reactiontime.services.GameService
import com.tokodeveloper.reactiontime.services.GameServiceImpl

class GameViewModel : ViewModel() {

    private val TAG = "GameViewModel"

    private val gameService: GameService = GameServiceImpl(GameModelImpl(200))

    private var _result = MutableLiveData<Result>()
    private var _gameState = MutableLiveData<HashMap<Int, String>>()
    private var _average = MutableLiveData<String>()
    private var _startVisibility = MutableLiveData<Int>()
    private var _waitVisibility = MutableLiveData<Int>()
    private var _stopVisibility = MutableLiveData<Int>()
    private var _restartVisibility = MutableLiveData<Int>()

    init {
        _startVisibility.value = View.VISIBLE
        _waitVisibility.value = View.GONE
        _stopVisibility.value = View.GONE
        _restartVisibility.value = View.GONE
    }

    val result: LiveData<Result> = _result

    val state: LiveData<HashMap<Int, String>> = _gameState

    val average: LiveData<String> = _average

    val startVisibility: LiveData<Int> = _startVisibility

    val waitVisibility: LiveData<Int> = _waitVisibility

    val stopVisibility: LiveData<Int> = _stopVisibility

    val restartVisibility: LiveData<Int> = _restartVisibility

    fun userClickedStartButton(view: View?) {
        waitVisible()
        gameService.start {
            stopVisible()
        }
    }

    fun userClickedStopButton(view: View?) {
        startVisible()

        gameService.stop {
            _result.value = it
            _gameState.value = gameService.state
            setAverage()
            checkFinished()
        }
    }

    private fun checkFinished() {
        val result = _result.value

        if (result is Success) {
            if (result.finished) {
                restartVisible()
            }
        } else if (result is Error) {

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

    fun userClickedRestartButton(view: View?) {
        startVisible()
        gameService.restart()
        _gameState.value = gameService.state
        setAverage()
    }

    private fun startVisible() {
        _startVisibility.postValue(View.VISIBLE)
        _waitVisibility.postValue(View.GONE)
        _stopVisibility.postValue(View.GONE)
        _restartVisibility.postValue(View.GONE)
    }

    private fun waitVisible() {
        _waitVisibility.postValue(View.VISIBLE)
        _stopVisibility.postValue(View.GONE)
        _startVisibility.postValue(View.GONE)
        _restartVisibility.postValue(View.GONE)
    }

    private fun stopVisible() {
        _stopVisibility.postValue(View.VISIBLE)
        _waitVisibility.postValue(View.GONE)
        _startVisibility.postValue(View.GONE)
        _restartVisibility.postValue(View.GONE)
    }

    private fun restartVisible() {
        _restartVisibility.postValue(View.VISIBLE)
        _stopVisibility.postValue(View.GONE)
        _waitVisibility.postValue(View.GONE)
        _startVisibility.postValue(View.GONE)
    }
}
