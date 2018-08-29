package com.tokodeveloper.reaction_time.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class MainMenuViewModel : ViewModel() {
    private val _startGame = MutableLiveData<Event<View>>()

    val startGame: LiveData<Event<View>> = _startGame

    fun userClicksStartGameButton(view: View?) {
        _startGame.value = Event(view!!)
    }
}
