package com.tokodeveloper.reactiontime.models

sealed class Result
class Success(val resultTime: Long, val finished: Boolean) : Result()
class Error : Result()