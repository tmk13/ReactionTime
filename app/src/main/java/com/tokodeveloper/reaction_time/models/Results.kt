package com.tokodeveloper.reaction_time.models

sealed class Result
class Success(val resultTime: Long) : Result()
class Error : Result()