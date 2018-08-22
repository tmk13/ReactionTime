package com.tokodeveloper.reactiontime.viewmodels

sealed class UIState
class StartState : UIState()
class StopState : UIState()
class RestartState : UIState()