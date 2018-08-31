package com.tokodeveloper.reaction_time.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainMenuViewModel : ViewModel() {
    private val _startGame = MutableLiveData<Event<Boolean>>()
    private val _leaderboard = MutableLiveData<Event<Boolean>>()
    private val _achievements = MutableLiveData<Event<Boolean>>()

    val startGame: LiveData<Event<Boolean>> = _startGame
    val leaderboard: LiveData<Event<Boolean>> = _leaderboard
    val achievements: LiveData<Event<Boolean>> = _achievements

    fun userClickedStartGameButton() {
        _startGame.value = Event(true)
    }

    fun userClickedLeaderboardButton() {
        _leaderboard.value = Event(true)
    }

    fun userClickedAchievementsButton() {
        _achievements.value = Event(true)
    }
}
