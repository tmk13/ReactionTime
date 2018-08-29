package com.tokodeveloper.reaction_time.viewmodels

sealed class UIState
class StartState : UIState()
class StopState : UIState()
class RestartState : UIState()