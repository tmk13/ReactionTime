package com.tokodeveloper.reaction_time.models

interface GameModel {
    fun start(callback: (Boolean) -> Unit)
    fun stop(callback: (Result) -> Unit)
    fun restart()
    val active: Boolean
    val state: HashMap<Int, String>
    val average: Int
    val finished: Boolean
}