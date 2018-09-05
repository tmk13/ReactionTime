package com.tokodeveloper.reaction_time.viewmodels

import androidx.lifecycle.*
import com.tokodeveloper.reaction_time.models.Error
import com.tokodeveloper.reaction_time.models.Result
import com.tokodeveloper.reaction_time.models.Success
import com.tokodeveloper.reaction_time.services.GameService
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class GameViewModel @Inject constructor(private val gameService: GameService) : ViewModel(), LifecycleObserver {

    private var job: Job? = null

    private var _gameState = MutableLiveData<HashMap<Int, String>>()
    private var _correct = MutableLiveData<List<Boolean>>()
    private var _average = MutableLiveData<String>()
    private var _finished = MutableLiveData<Boolean>()
    private var _showError = MutableLiveData<Boolean>()
    private var _startVisible = MutableLiveData<Boolean>()
    private var _waitVisible = MutableLiveData<Boolean>()
    private var _stopVisible = MutableLiveData<Boolean>()
    private var _restartVisible = MutableLiveData<Boolean>()

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
        reset()

        _startVisible.value = true
        _waitVisible.value = false
        _stopVisible.value = false
        _restartVisible.value = false
        _finished.value = false

        showAverage = Transformations.map(_average) { it.isNotEmpty() }
        correct = Transformations.map(_gameState) { it.values.map { !it.endsWith("!") } }
    }

    companion object {
        private const val TAG = "GameViewModel"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun reset() {
        job?.cancel()
        gameService.restart()
        _gameState.postValue(gameService.state)
        _correct.postValue(emptyList())
        _finished.postValue(false)
        setAverage()
        startVisible()
    }

    fun userClickedStartButton() {
        _finished.postValue(false)
        waitVisible()

        job = launch(CommonPool) {
            gameService.start {
                launch(UI) {
                    stopVisible()
                }
            }
        }
    }

    fun userClickedWaitButton() {
        reset()
        _gameState.postValue(gameService.state)
        showError()
        startVisible()
    }

    fun userClickedStopButton() {
        startVisible()

        launch(UI) {
            val result = withContext(CommonPool) {
                gameService.stop()
            }
            checkResult(result)
        }
    }

    private fun checkResult(result: Result) {
        when (result) {
            is Success -> {
                _gameState.postValue(gameService.state)
                setAverage()
                checkFinished()
            }
            is Error -> showError()
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
            _finished.value = fin
            restartVisible()
        }
    }

    private fun setAverage() {
        val average = gameService.average
        if (average > 0) {
            _average.value = average.toString()
        } else {
            _average.value = ""
        }
    }

    fun userClickedRestartButton() {
        startVisible()
        reset()
        setAverage()
    }

    private fun startVisible() {
        _startVisible.postValue(true)

        _waitVisible.postValue(false)
        _stopVisible.postValue(false)
        _restartVisible.postValue(false)
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
