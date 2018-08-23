package com.tokodeveloper.reactiontime.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private var _averageVisibility = MutableLiveData<Int>()
    private var _errorVisibility = MutableLiveData<Int>()
    private var _errorValue = MutableLiveData<Int>()
    private var _startVisibility = MutableLiveData<Int>()
    private var _waitVisibility = MutableLiveData<Int>()
    private var _stopVisibility = MutableLiveData<Int>()
    private var _restartVisibility = MutableLiveData<Int>()

    init {
        _startVisibility.value = View.VISIBLE
        _waitVisibility.value = View.GONE
        _stopVisibility.value = View.GONE
        _restartVisibility.value = View.GONE
        _errorVisibility.value = View.GONE
        _finished.value = false
    }

    val result: LiveData<Result> = _result

    val state: LiveData<HashMap<Int, String>> = _gameState

    val correct: LiveData<List<Boolean>> = _correct

    val average: LiveData<String> = _average

    val finished: LiveData<Boolean> = _finished

    val averageVisibility: LiveData<Int> = _averageVisibility

    val errorVisibility: LiveData<Int> = _errorVisibility

    val errorValue: LiveData<Int> = _errorValue

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

    fun userClickedWaitButton(view: View?) {
        gameService.restart()
        _gameState.postValue(gameService.state)
        startVisible()
    }

    fun userClickedStopButton(view: View?) {
        startVisible()

        gameService.stop {
            _result.value = it
            _gameState.postValue(gameService.state)
            _correct.postValue(gameService.state.map { myMap -> !myMap.value.endsWith("!") })
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
        _averageVisibility.postValue(View.GONE)
        _errorVisibility.postValue(View.VISIBLE)
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

    fun userClickedRestartButton(view: View?) {
        startVisible()
        gameService.restart()
        _gameState.postValue(gameService.state)
        _correct.postValue(emptyList())
        setAverage()
    }

    private fun startVisible() {
        _startVisibility.postValue(View.VISIBLE)
        _averageVisibility.postValue(View.VISIBLE)
        _errorVisibility.postValue(View.GONE)
        _waitVisibility.postValue(View.GONE)
        _stopVisibility.postValue(View.GONE)
        _restartVisibility.postValue(View.GONE)
        _finished.postValue(false)
    }

    private fun waitVisible() {
        _waitVisibility.postValue(View.VISIBLE)
        _averageVisibility.postValue(View.VISIBLE)
        _errorVisibility.postValue(View.GONE)
        _stopVisibility.postValue(View.GONE)
        _startVisibility.postValue(View.GONE)
        _restartVisibility.postValue(View.GONE)
    }

    private fun stopVisible() {
        _stopVisibility.postValue(View.VISIBLE)
        _averageVisibility.postValue(View.VISIBLE)
        _errorVisibility.postValue(View.GONE)
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
