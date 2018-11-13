package com.tokodeveloper.reaction_time.models

interface GameModel {
    suspend fun start()
    suspend fun stop(): Result
    fun restart()
    val active: Boolean
    val state: HashMap<Int, String>
    val average: Int
    val finished: Boolean
}