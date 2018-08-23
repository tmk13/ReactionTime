package com.tokodeveloper.reactiontime.models

sealed class Result
class Success(val resultTime: Long) : Result()
class Error : Result()